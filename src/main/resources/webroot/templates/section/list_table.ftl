<#-- This table uses the database column names as the table columns. It automagically populates the data in the
 table from the inputs. This is using the Freemarker template system to populate the data.-->
<div class="dropdown" id="${subj}-dropdown">
    <div id="${subj}-table">
        <div class="container-table" >
            <div class="row">
                <div class="col-md-12">
                    <#-- Here we populates the entries based on the column names and the number of rows. -->
                    <div class="table-responsive" style="max-height: 70vh;">
                        <table id="data" class="table table-striped table-hover table-bordered spacing-xs">
                            <#-- HEADER -->
                            <thead id="tableHead" class="table-header">
                                    ATHLETE DATA
                            </thead>
                            <#-- BODY -->
                            <tbody id="tableBody">
                                <#if table_data??>
                                    <#list table_data as row>
                                        <tr>
                                            <td>
                                                <#if row??>
                                                    <a href="${row}" target="_blank">  Link: ${row}</a>
                                                <#else>
                                                    "nothing here"
                                                </#if>
                                            </td>
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
