/*
 * Copyright 1999-2011 Alibaba Group.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package owen.galga.rpc.config;

/**
 * @author Owen.Wang
 * @description: RPC客户端
 * @date 2020/5/30 14:23
 */

public class ProviderConfig<T> extends AbstractServiceConfig {

    private String alias;

    private Class<T> interfaceI;

    private Integer timeouit;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Class<T> getInterfaceI() {
        return interfaceI;
    }

    public void setInterfaceI(Class<T> interfaceI) {
        this.interfaceI = interfaceI;
    }

    public Integer getTimeouit() {
        return timeouit;
    }

    public void setTimeouit(Integer timeouit) {
        this.timeouit = timeouit;
    }
}