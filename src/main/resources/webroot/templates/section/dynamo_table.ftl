<#-- This table uses the database column names as the table columns. It automagically populates the data in the
 table from the inputs. This is using the Freemarker template system to populate the data.-->
<div class="dropdown" id="${subj}-dropdown">
    <div id="${subj}-table">
        <div class="container-table" >
            <div class="row">
                <div class="col-md-12">
                        <h2 class="z-n1 mt-4 mb-5">${details_type}
                            <br>Domain: <span class="act-like-h4">${subj}</span>
                        </h2>
                        <#-- Here we populates the entries based on the column names and the number of rows. -->
                        <div class="table-responsive" style="max-height: 70vh;">
                            <table id="data" class="table table-striped table-hover table-bordered spacing-xs">
                                <#-- Get the column names and store them for later. -->
                                <#assign keys = table_data[0]?keys>
                                <#-- HEADER -->
                                <thead id="tableHead" class="table-header">
                                <#list keys as prop>
                                    <td>${prop}</td>
                                </#list>
                                </thead>
                                <#-- BODY -->
                                <tbody id="tableBody">
                                <#if sort_by??>
                                    <#list table_data?sort_by("${sort_by}")?reverse as stats>
                                        <tr>
                                            <#list table_data[0] as k, propValue>
                                                <td><p class="eyebrow">
                                                        <#if stats[k]??>
                                                            ${stats[k]}
                                                        <#else>
                                                            ""
                                                        </#if>
                                                    </p></td>
                                            </#list>
                                        </tr>
                                    </#list>
                                <#else>
                                    <#list table_data as stats>
                                        <tr>
                                            <#list table_data[0] as k, propValue>
                                                <td><p class="eyebrow">
                                                        <#if stats[k]??>
                                                            ${stats[k]}
                                                        <#else>
                                                            ""
                                                        </#if>
                                                    </p></td>
                                            </#list>
                                        </tr>
                                    </#list>
                                </#if>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        <hr class="mt-4 mb-5">
        </div>
    </div>

