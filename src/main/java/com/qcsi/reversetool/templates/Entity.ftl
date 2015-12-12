public class ${table.name?cap_first}{

    <#list table.columns as column>
        private ${column.type} ${column.name};
    </#list>

    <#list table.columns as column>
        public ${column.type} get${column.name?cap_first}(){
            return ${column.name};
        }

    </#list>
    <#list table.columns as column>
        public void set${column.name?cap_first}(${column.type} ${column.name}){
            this.${column.name} = ${column.name};
        }

    </#list>
}