package org.codehaus.mojo.buildmetadata;

public enum JavaEnvironment
{
    JAVA_RUNTIME_NAME( "java.runtime.name" ),
    JAVA_RUNTIME_VERSION( "java.runtime.version" ),

    JAVA_SPECIFICATION_NAME( "java.specification.name" ),
    JAVA_SPECIFICATION_VENDOR( "java.specification.vendor" ),
    JAVA_SPECIFICATION_VERSION( "java.specification.version" ),

    JAVA_VENDOR( "java.vendor" ),
    JAVA_VERSION( "java.version" ),

    JAVA_VM_NAME( "java.vm.name" ),
    JAVA_VM_INFO( "java.vm.info" ),
    JAVA_VM_VENDOR( "java.vm.vendor" ),
    JAVA_VM_VERSION( "java.vm.version" ),

    JAVA_VM_SPECIFICATION_NAME( "java.vm.specification.name" ),
    JAVA_VM_SPECIFICATION_VENDOR( "java.vm.specification.vendor" ),
    JAVA_VM_SPECIFICATION_VERSION( "java.vm.specification.version" ),

    SUN_MANAGEMENT_COMPILER( "sun.management.compiler" );

    private String value;

    public String getValue()
    {
        return this.value;
    }

    private JavaEnvironment( String name )
    {
        this.value = name;
    }
}