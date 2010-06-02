/**
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.apache.bval.model;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Description: the meta description of a property of a bean. it supports a map
 * of features and multiple validations<br/>
 *
 * @see Validation
 * @see MetaBean
 */
public class MetaProperty extends FeaturesCapable
      implements Cloneable, Features.Property {
    private static final long serialVersionUID = 1L;

    private String name;

    private Type type;
    private MetaBean metaBean;
    private MetaBean parentMetaBean;

    public MetaProperty() {
    }

    /** the metabean of the target bean (mainly for relationships) */
    public MetaBean getMetaBean() {
        return metaBean;
    }

    public void setMetaBean(MetaBean metaBean) {
        this.metaBean = metaBean;
    }

  /**
   * the metabean that owns this property (set by MetaBean.putProperty())
   */
    public MetaBean getParentMetaBean() {
        return parentMetaBean;
    }

    public void setParentMetaBean(MetaBean parentMetaBean) {
        this.parentMetaBean = parentMetaBean;
    }    

    public boolean isRelationship() {
        return metaBean != null;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public Class<?> getTypeClass() {
        return getTypeClass(type);
    }

    private Class<?> getTypeClass(Type rawType) {
        if (rawType instanceof Class<?>) {
            return (Class<?>) rawType;
        } else if (rawType instanceof ParameterizedType) {
            return getTypeClass(((ParameterizedType) rawType).getRawType()); // recursion!
        } else if(rawType instanceof DynaType) {
            return getTypeClass(((DynaType)rawType).getRawType()); // recursion
        } else {
            return null; // class cannot be determined!
        }
    }

    public String getName() {
        return name;
    }

    public boolean isMandatory() {
        return getFeature(MANDATORY, Boolean.FALSE).booleanValue();
    }

    public void setMandatory(boolean mandatory) {
        putFeature(MANDATORY, Boolean.valueOf(mandatory));
    }

    @Deprecated // remove this method?
    public String[] getJavaScriptValidations() {
        return getFeature(JAVASCRIPT_VALIDATION_FUNCTIONS);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return "MetaProperty{" + "name='" + name + '\'' + ", type=" + type + '}';
    }
}