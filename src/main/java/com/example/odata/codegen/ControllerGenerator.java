package com.example.odata.codegen;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.edm.EdmKeyPropertyRef;
import org.apache.olingo.commons.api.edm.EdmProperty;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.List;

/**
 * EntitySet → REST Controller 생성. Service 위임.
 */
final class ControllerGenerator {

    private final GenConfig cfg;

    ControllerGenerator(GenConfig cfg) {
        this.cfg = cfg;
    }

    void generate(EdmEntitySet entitySet) throws IOException {
        EdmEntityType et = entitySet.getEntityType();
        String entity = et.getName();
        String setName = entitySet.getName();
        String className = entity + "Controller";
        String fieldName = decapitalize(entity) + "Service";
        String basePath = "/api/" + setName;

        ClassName responseType = ClassName.get(cfg.dtoPackage(), entity + "Response");
        ClassName requestType = ClassName.get(cfg.dtoPackage(), entity + "Request");
        ClassName serviceType = ClassName.get(cfg.servicePackage(), entity + "Service");
        ClassName responseEntity = ClassName.get("org.springframework.http", "ResponseEntity");

        EdmKeyPropertyRef keyRef = et.getKeyPropertyRefs().get(0);
        EdmProperty keyProp = (EdmProperty) et.getProperty(keyRef.getName());
        TypeName keyType = keyJavaType(keyProp);

        ClassName getMapping = ClassName.get("org.springframework.web.bind.annotation", "GetMapping");
        ClassName postMapping = ClassName.get("org.springframework.web.bind.annotation", "PostMapping");
        ClassName patchMapping = ClassName.get("org.springframework.web.bind.annotation", "PatchMapping");
        ClassName deleteMapping = ClassName.get("org.springframework.web.bind.annotation", "DeleteMapping");
        ClassName pathVariable = ClassName.get("org.springframework.web.bind.annotation", "PathVariable");
        ClassName requestBody = ClassName.get("org.springframework.web.bind.annotation", "RequestBody");
        ClassName requestParam = ClassName.get("org.springframework.web.bind.annotation", "RequestParam");

        // @RequestParam(required = false) String expand
        ParameterSpec expandParam = ParameterSpec.builder(String.class, "expand")
                .addAnnotation(AnnotationSpec.builder(requestParam)
                        .addMember("required", "$L", false).build())
                .build();

        FieldSpec serviceField = FieldSpec.builder(serviceType, fieldName, Modifier.PRIVATE, Modifier.FINAL).build();

        // GET 목록
        TypeName listResp = ParameterizedTypeName.get(ClassName.get(List.class), responseType);
        MethodSpec list = MethodSpec.methodBuilder("list")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(getMapping)
                .returns(ParameterizedTypeName.get(responseEntity, listResp))
                .addParameter(expandParam)
                .addStatement("return $T.ok($N.list(expand))", responseEntity, serviceField)
                .build();

        ParameterSpec keyParam = ParameterSpec.builder(keyType, "key")
                .addAnnotation(pathVariable)
                .build();

        // GET 단건
        MethodSpec getOne = MethodSpec.methodBuilder("getByKey")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(AnnotationSpec.builder(getMapping).addMember("value", "$S", "/{key}").build())
                .returns(ParameterizedTypeName.get(responseEntity, responseType))
                .addParameter(keyParam)
                .addParameter(expandParam)
                .addStatement("return $T.ok($N.getByKey(key, expand))", responseEntity, serviceField)
                .build();

        // POST 생성
        ParameterSpec bodyParam = ParameterSpec.builder(requestType, "request")
                .addAnnotation(requestBody)
                .build();
        MethodSpec create = MethodSpec.methodBuilder("create")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(postMapping)
                .returns(ParameterizedTypeName.get(responseEntity, responseType))
                .addParameter(bodyParam)
                .addStatement("return $T.ok($N.create(request))", responseEntity, serviceField)
                .build();

        // PATCH 수정
        MethodSpec update = MethodSpec.methodBuilder("update")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(AnnotationSpec.builder(patchMapping).addMember("value", "$S", "/{key}").build())
                .returns(ParameterizedTypeName.get(responseEntity, responseType))
                .addParameter(keyParam)
                .addParameter(bodyParam)
                .addStatement("return $T.ok($N.update(key, request))", responseEntity, serviceField)
                .build();

        // DELETE 삭제
        MethodSpec delete = MethodSpec.methodBuilder("delete")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(AnnotationSpec.builder(deleteMapping).addMember("value", "$S", "/{key}").build())
                .returns(ParameterizedTypeName.get(responseEntity, ClassName.get(Void.class)))
                .addParameter(keyParam)
                .addStatement("$N.delete(key)", serviceField)
                .addStatement("return $T.noContent().build()", responseEntity)
                .build();

        TypeSpec type = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .addJavadoc("$L REST Controller — 생성 코드.\n", entity)
                .addAnnotation(ClassName.get("org.springframework.web.bind.annotation", "RestController"))
                .addAnnotation(AnnotationSpec.builder(
                                ClassName.get("org.springframework.web.bind.annotation", "RequestMapping"))
                        .addMember("value", "$S", basePath).build())
                .addAnnotation(ClassName.get("lombok", "RequiredArgsConstructor"))
                .addField(serviceField)
                .addMethod(list)
                .addMethod(getOne)
                .addMethod(create)
                .addMethod(update)
                .addMethod(delete)
                .build();

        JavaFile.builder(cfg.controllerPackage(), type)
                .skipJavaLangImports(true)
                .build()
                .writeTo(cfg.outputDir());
    }

    private TypeName keyJavaType(EdmProperty keyProp) {
        String fqn = keyProp.getType().getFullQualifiedName().getFullQualifiedNameAsString();
        TypeName t = EdmTypeMapper.primitiveOrNull(fqn);
        return t != null ? t : ClassName.get(String.class);
    }

    private static String decapitalize(String s) {
        return Character.toLowerCase(s.charAt(0)) + s.substring(1);
    }
}
