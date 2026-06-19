package com.example.odata.codegen;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import org.apache.olingo.commons.api.edm.EdmEntitySet;
import org.apache.olingo.commons.api.edm.EdmEntityType;
import org.apache.olingo.commons.api.edm.EdmKeyPropertyRef;
import org.apache.olingo.commons.api.edm.EdmProperty;

import javax.lang.model.element.Modifier;
import java.io.IOException;

/**
 * EntitySet → Service 생성.
 * CRUD 로직은 {@code AbstractODataService} 에 공통화. 여기선 엔티티셋명/타입 토큰만 제공하는 얇은 서브클래스 생성.
 */
final class ServiceGenerator {

    private static final ClassName ABSTRACT_SERVICE =
            ClassName.get("com.example.odata.support", "AbstractODataService");
    private static final ClassName COLLECTION =
            ClassName.get("com.example.odata.support", "ODataCollection");
    private static final ClassName WEB_CLIENT =
            ClassName.get("org.springframework.web.reactive.function.client", "WebClient");
    private static final ClassName PTR =
            ClassName.get("org.springframework.core", "ParameterizedTypeReference");
    private static final ClassName SERVICE_ANN =
            ClassName.get("org.springframework.stereotype", "Service");
    private static final ClassName OVERRIDE = ClassName.get(Override.class);

    private final GenConfig cfg;

    ServiceGenerator(GenConfig cfg) {
        this.cfg = cfg;
    }

    void generate(EdmEntitySet entitySet) throws IOException {
        EdmEntityType et = entitySet.getEntityType();
        String entity = et.getName();
        String setName = entitySet.getName();
        String className = entity + "Service";

        ClassName responseType = ClassName.get(cfg.dtoPackage(), entity + "Response");
        ClassName requestType = ClassName.get(cfg.dtoPackage(), entity + "Request");

        // 키 타입 (composite 면 첫 키)
        EdmKeyPropertyRef keyRef = et.getKeyPropertyRefs().get(0);
        EdmProperty keyProp = (EdmProperty) et.getProperty(keyRef.getName());
        TypeName keyType = keyJavaType(keyProp);

        // extends AbstractODataService<XxxResponse, XxxRequest, KeyType>
        TypeName superType = ParameterizedTypeName.get(ABSTRACT_SERVICE, responseType, requestType, keyType);

        FieldSpec setField = FieldSpec.builder(String.class, "ENTITY_SET",
                        Modifier.PRIVATE, Modifier.STATIC, Modifier.FINAL)
                .initializer("$S", setName).build();

        // 생성자 — super(webClient)
        MethodSpec ctor = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PUBLIC)
                .addParameter(WEB_CLIENT, "odataWebClient")
                .addStatement("super(odataWebClient)")
                .build();

        MethodSpec entitySetM = MethodSpec.methodBuilder("entitySet")
                .addAnnotation(OVERRIDE).addModifiers(Modifier.PROTECTED)
                .returns(String.class)
                .addStatement("return ENTITY_SET")
                .build();

        MethodSpec responseTypeM = MethodSpec.methodBuilder("responseType")
                .addAnnotation(OVERRIDE).addModifiers(Modifier.PROTECTED)
                .returns(ParameterizedTypeName.get(ClassName.get(Class.class), responseType))
                .addStatement("return $T.class", responseType)
                .build();

        // new ParameterizedTypeReference<ODataCollection<XxxResponse>>(){}
        TypeName collOfResp = ParameterizedTypeName.get(COLLECTION, responseType);
        TypeName refType = ParameterizedTypeName.get(PTR, collOfResp);
        TypeSpec refAnon = TypeSpec.anonymousClassBuilder("").superclass(refType).build();
        MethodSpec collectionTypeM = MethodSpec.methodBuilder("collectionType")
                .addAnnotation(OVERRIDE).addModifiers(Modifier.PROTECTED)
                .returns(refType)
                .addStatement("return $L", refAnon)
                .build();

        TypeSpec type = TypeSpec.classBuilder(className)
                .addModifiers(Modifier.PUBLIC)
                .superclass(superType)
                .addJavadoc("$L OData Service — 생성 코드. CRUD 는 AbstractODataService 참고.\n", entity)
                .addAnnotation(SERVICE_ANN)
                .addField(setField)
                .addMethod(ctor)
                .addMethod(entitySetM)
                .addMethod(responseTypeM)
                .addMethod(collectionTypeM)
                .build();

        JavaFile.builder(cfg.servicePackage(), type)
                .skipJavaLangImports(true)
                .build()
                .writeTo(cfg.outputDir());
    }

    private TypeName keyJavaType(EdmProperty keyProp) {
        String fqn = keyProp.getType().getFullQualifiedName().getFullQualifiedNameAsString();
        TypeName t = EdmTypeMapper.primitiveOrNull(fqn);
        return t != null ? t : ClassName.get(String.class);
    }
}
