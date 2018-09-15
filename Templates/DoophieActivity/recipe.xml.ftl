<?xml version="1.0"?>
<recipe>
 
    <instantiate from="src/app_package/Dependency.kt.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${className}/${className}Dependency.kt" />
    <instantiate from="src/app_package/DoophieActivity.kt.ftl"
                   to="${escapeXmlAttribute(srcOut)}/${className}/${className}Activity.kt" />

    <instantiate from="res/layout/activity_doophie.xml.ftl"
                   to="${escapeXmlAttribute(resOut)}/layout/activity_${className?lower_case}.xml" />
 
    <open file="${srcOut}/${className}Activity.kt"/>
</recipe>
