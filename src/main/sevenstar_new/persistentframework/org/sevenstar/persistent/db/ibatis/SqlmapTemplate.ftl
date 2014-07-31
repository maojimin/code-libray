<?xml version="1.0" encoding="UTF-8" standalone="no"?>
<!DOCTYPE sqlMap PUBLIC "-//iBATIS.com//DTD SQL Map 2.0//EN" "http://www.ibatis.com/dtd/sql-map-2.dtd">
<sqlMap namespace="${domain.simpleClassName}_base">
   <#if (domain.cacheModel)?? >
    <cacheModel id="${domain.simpleClassName}_base_cache" type ="${domain.cacheModel.type}" readOnly="${domain.cacheModel.readonly }" serialize="${domain.cacheModel.serialize}">
		<flushInterval minutes="${domain.cacheModel.flushinterval}"/>
		<#list domain.cacheModel.getflushonexecuteList() as execute>
		   <flushOnExecute statement="${execute}"/>
		</#list>
		<property name="cache-size" value="${domain.cacheModel.cachesize}" />
   </cacheModel>
   </#if>
   <#if (domain.ibatisModel.insertParameterList.size() > 0) >
	<parameterMap id="${domain.simpleClassName}_base_parameter_insert" class="${domain.className}">
         <#list domain.ibatisModel.insertParameterList as insert>
           ${insert}
        </#list>
	</parameterMap>
	</#if>
	<#if (domain.ibatisModel.deleteParameterList.size() > 0) >
	<parameterMap id="${domain.simpleClassName}_base_parameter_deleteload" class="${domain.className}">
       <#list domain.ibatisModel.deleteParameterList as delete>
           ${delete}
        </#list>
	</parameterMap>
	</#if>
	<#if (domain.ibatisModel.updateParameterList.size() > 0) >
	<parameterMap id="${domain.simpleClassName}_base_parameter_update" class="${domain.className}">
        <#list domain.ibatisModel.updateParameterList as update>
           ${update}
        </#list>
	</parameterMap>
	</#if>
	<resultMap id="${domain.simpleClassName}_base_result" class="${domain.className}">
        <#list domain.ibatisModel.resultList as result>
           ${result}
        </#list>
	</resultMap>
	<#if ( domain.ibatisModel.sqlSeq.length()>0 ) >
	<select id="${domain.simpleClassName}_base_selectseq" resultClass="java.lang.Long" >
		<![CDATA[
		        ${domain.ibatisModel.sqlSeq}
		]]>
	 </select>
	 </#if>
	 <insert id="${domain.simpleClassName}_base_insert" parameterClass="${domain.className}">
			  ${domain.ibatisModel.sqlInsert}
	 </insert>
	 <update id="${domain.simpleClassName}_base_update" parameterClass="${domain.className}">
		         ${domain.ibatisModel.sqlUpdate}
	  </update>
	  
	  <update id="${domain.simpleClassName}_base_update_null" parameterClass="${domain.className}">
		         ${domain.ibatisModel.sqlUpdateNull}
	  </update>
	  <delete id="${domain.simpleClassName}_base_delete" parameterMap="${domain.simpleClassName}_base_parameter_deleteload">
                 ${domain.ibatisModel.sqlDelete}
          </delete>
	  <select id="${domain.simpleClassName}_base_load" resultMap="${domain.simpleClassName}_base_result" parameterMap="${domain.simpleClassName}_base_parameter_deleteload" <#if (domain.cacheModel)?? && (domain.cacheModel.type.length() > 0) > cacheModel="${domain.simpleClassName}_base_cache" </#if>>
		    ${domain.ibatisModel.sqlLoad}
	  </select>
	  <#if (domain.getId().getPropertyList().size() == 1) >
	  <select id="${domain.simpleClassName}_base_load_for_relation" parameterClass="${domain.id.getFirstProperty().getClassName()}" resultMap="${domain.simpleClassName}_base_result" <#if (domain.cacheModel)?? && (domain.cacheModel.type.length() > 0) > cacheModel="${domain.simpleClassName}_base_cache" </#if> >
		    select * from ${domain.table.name} where ${domain.id.getFirstProperty().column.name} = #value#
	  </select>
	  </#if>
	  <select id="${domain.simpleClassName}_base_selectall" resultMap="${domain.simpleClassName}_base_result" <#if (domain.cacheModel)?? && (domain.cacheModel.type.length() > 0) > cacheModel="${domain.simpleClassName}_base_cache" </#if> >
 		    ${domain.ibatisModel.sqlSelectAll}
	  </select>
	  <select id="${domain.simpleClassName}_base_select" resultMap="${domain.simpleClassName}_base_result" parameterClass="${domain.className}">
		    ${domain.ibatisModel.sqlSelect}
	  </select>
	  <select id="${domain.simpleClassName}_base_select_maxsize" resultMap="${domain.simpleClassName}_base_result" parameterClass="${domain.className}">
		    ${domain.ibatisModel.sqlSelectMaxSize}
	  </select>
	  <select id="${domain.simpleClassName}_base_page_select" resultMap="${domain.simpleClassName}_base_result" parameterClass="${domain.className}">
 		     ${(dialet.getPageBeforeSql(domain))!''}
		    ${domain.ibatisModel.pageSqlSelect}
		      ${(dialet.getPageAfterSql(domain))!''}
	  </select>
	  <select id="${domain.simpleClassName}_base_page_select_count" resultClass="string" parameterClass="${domain.className}">
		    ${domain.ibatisModel.sqlSelectCount}
	  </select>

	  <select id="${domain.simpleClassName}_base_select_equal" resultMap="${domain.simpleClassName}_base_result" parameterClass="${domain.className}">
		    ${domain.ibatisModel.sqlSelectEqual}
	  </select>
	   <select id="${domain.simpleClassName}_base_select_equal_maxsize" resultMap="${domain.simpleClassName}_base_result" parameterClass="${domain.className}">
		    ${domain.ibatisModel.sqlSelectEqualMaxSize}
	  </select>
	  <select id="${domain.simpleClassName}_base_select_equal_count" resultClass="string" parameterClass="${domain.className}">
		    ${domain.ibatisModel.sqlSelectEqualCount}
	  </select>
	   <delete id="${domain.simpleClassName}_base_delete_equal"  parameterClass="${domain.className}">
		    ${domain.ibatisModel.sqlDeleteEqual}
	  </delete>
	   <#list domain.manyToOneList as field>
              <select id="${domain.simpleClassName}_base_select_${field.column.name}"   resultMap="${domain.simpleClassName}_base_result"  >
          <![CDATA[         select ${(domain.ibatisModel.selectHeader)!'*'} from ${domain.table.name} where
                 <#if  (domain.condition)?? && (domain.condition != "") >
                     ${domain.condition} and
                 </#if>
                     ${field.column.name} = #value#
                       ]]>
                    <#if domain.order?? && domain.order!="">
                      order by ${domain.order}
                    </#if>
               
            </select>
	  </#list>
	  <#list domain.oneToManyList as field>
            <#if  (field.table) ?? && ( field.table.length() > 0)  >
            <select id="${field.select}" resultClass="${field.className}"  >
                select * from ${field.table} where ${field.column.name} = #value#
            </select>
            </#if>
 	  </#list>
 	  <#list domain.oneToOneList as field>
            <#if  (field.table) ?? && ( field.table.length() > 0)  >
            <select id="${field.select}" resultClass="${field.className}"  >
                select * from ${field.table} where ${field.column.name} = #value#
            </select>
            </#if>
 	  </#list>
 	   <#list domain.manyToOneList as field>
           <#if  (field.table) ?? && (field.table.length() > 0 ) >
             <select id="${field.select}" resultClass="${field.className}"  >
                select * from ${field.table} where ${field.column} = #value#
            </select>
            </#if>
       </#list>
</sqlMap>
