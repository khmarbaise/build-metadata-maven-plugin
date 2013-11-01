package org.codehaus.mojo.buildmetadata;

/*
 * The MIT License
 *
 * Copyright (c) 2004, The Codehaus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

/**
 * @author Karl-Heinz Marbaise
 */
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

    /**
     * Return the value.
     * @return value.
     */
    public String getValue()
    {
        return this.value;
    }

    /**
     * private constructor.
     * @param name
     */
    private JavaEnvironment( String name )
    {
        this.value = name;
    }
}