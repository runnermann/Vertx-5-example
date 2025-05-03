<#-- METRICS -->
<section id="gauge_section">
    <div class="container cb-container" ng-controller="StatsController">
        <div class="row mt-2">
            <div class="row mb-5">
                <div class="col-md-4 text-center">
                    <h2 class="title">KnocScore Metrics</h2>
                </div>
            </div>
            <div class="row mt-3">

                <#-- Data Origins -->
                <#include "switches.ftl">

                <hr class="mt-4 mb-5">
            </div>
            <#-- HEADER -->
            <div class="row" id="header1">
                <#-- Left Column spacer -->
                <div class="col-2 col-sm-2 col-lg-2 d-none d-md-block">
                    <label>VALUE</label>
                </div>
                <div class="col-md-2 d-none d-md-block">
                    <label>PERCENTILE</label>
                </div>
                <div class="col-md-2 d-none d-md-block">
                    <label>PUBLICATIONS / PATENTS</label>
                </div>
                <div class="col-md-3 d-none d-md-block">
                    <label>HOURS</label>
                </div>
                <div class="col-md-3 d-none d-md-block">
                    <label>TESTING</label>
                </div>
                <hr class="mt-4 d-none d-md-block mb-4">
            </div>


            <#-- Stats GAUGE rows. -->
            <#list qrStatsData?sort_by("totalHrs")?reverse as stats>
            <#-- To prevent catagories from showing that have no hours.
             We filter them in the server. But we check for an empty subj.
             here since it will be empty, the row will have data, but will
             cause an error.-->
                <#if stats.totalHrs?? && stats.totalHrs != 0>
                    <div class="justify-content-center" id="${stats.subj}-div">

                        <div class="row">
                            <div class="col-mb-2 col-lg-2 mb-5">
                                <div class="stats-backgnd">
                                    <h2 class="act-like-h4">${stats.subj}</h2>
                                    <#if stats.amount != "0">
                                        <div class="earnings mt-5">${stats.amount}</div>
                                        <div class="period">${stats.amountTime}</div>
                                    </#if>
                                </div>
                            </div>
                            <#-- PERCENTILE -->
                            <div class="col-md-2 col-lg-2 d-flex align-items-center justify-content-center mb-5">
                                <div class="stats-backgnd">
                                    <#if stats.initialpercentile??>
                                        <h3>Percentile:</h3>
                                        <p class="percentile">Top <span class="act-like-h1 mt-5 mb-5">${stats.initialpercentile}%</span>
                                            <br>at <a href="/"> ${stats.initentity}</a></p>
                                        <nav>
                                            <menu>
                                                <menuitem id="demo1" class="mt-3">
                                                    <button class="nav-link nav-btn">see details</button>
                                                    <menu>
                                                        <menuitem id="btn-ent-${stats.subj}" ng-click="getElements('${stats.hash}', '${stats.subj}')"><a class="menu-item">Employer/Entity</a></menuitem>
                                                        <menuitem id="btn-kno-${stats.subj}" ng-click="getKnowledge('${stats.hash}', '${stats.subj}')"><a class="menu-item">Knowledge</a></menuitem>
                                                        <menuitem id="btn-peep-${stats.subj}" ng-click="getPeople('${stats.hash}', '${stats.subj}')"><a class="menu-item">People</a></menuitem>
                                                        <menuitem id="btn-pos-${stats.subj}" ng-click="getPositions('${stats.hash}', '${stats.subj}')"><a class="menu-item">Positions</a></menuitem>
                                                        <menuitem id="btn-pro-${stats.subj}" ng-click="getProjects('${stats.hash}', '${stats.subj}')"><a class="menu-item">Projects</a></menuitem>
                                                        <menuitem id="btn-ach-${stats.subj}" ng-click="getAchievements('${stats.hash}', '${stats.subj}')"><a class="menu-item">Achievements</a></menuitem>
                                                    </menu>
                                                </menuitem>
                                            </menu>
                                        </nav>
                                    </#if>
                                </div>
                            </div>
                            <#-- CITATIONS/PATENTS -->
                            <div class="col-md-2 col-lg-2 d-flex align-items-center">
                                <div class="stats-backgnd">
                                    <table>
                                        <#if stats.patents??>
                                            <tr>
                                                <td><p class="pubs-label">Patents:</p></td>
                                                <td><p class="eyebrow-md">${stats.patents}</p></td>
                                            </tr>
                                        </#if>
                                        <#if stats.citations??>
                                            <tr>
                                                <td><p class="pubs-label">Citations:</p></td>
                                                <td><p class="eyebrow-md">${stats.citations}</p></td>
                                            </tr>
                                        </#if>
                                    </table>
                                </div>
                            </div>
                            <#-- The HOURS GAUGE -->
                            <div class="col-md-4 col-lg-3 d-flex border-lt-1 align-items-center justify-content-center">
                                <canvas id="${stats.subj_ctx}"></canvas>

                                <script>
                                    let ${stats.subj_ctx}_ctx = document.getElementById('${stats.subj_ctx}').getContext('2d');
                                    // Center label
                                    let ${stats.subj_ctx}_center_label = {
                                        id: '${stats.subj_ctx}_label',
                                        afterDraw(chart, args, pluginOptions) {
                                            const{ ctx, data } = chart;
                                            ctx.save();
                                            const x = chart.getDatasetMeta(0).data[0].x;
                                            const y = chart.getDatasetMeta(0).data[0].y;
                                            ctx.font = 'bold 16px sans-serif';
                                            ctx.fillStyle = 'rgb(0,0,0)';
                                            ctx.textAlign = 'center';
                                            ctx.textBaseline = 'middle';
                                            ctx.fillText('${stats.totalHrs} hrs', x, y);
                                        }
                                    }
                                    // Outer Label
                                    let ${stats.subj_ctx}_outer_labels = {
                                        id: '${stats.subj_ctx}_outer_labels',
                                        afterDraw(chart, args, options) {
                                            const {ctx, chartArea: { top, bottom, left, right, width, height }} = chart;
                                            const labels = ['Create', 'Study', 'Discuss', 'Practice'];
                                            const dtx = [ ${stats.create},${stats.study},${stats.discuss},${stats.practical}];

                                            chart.data.datasets.forEach((dataset, i) => {
                                                chart.getDatasetMeta(i).data.forEach((datapoint, index) => {

                                                    if(dtx[index] && dtx[index] > 2) {

                                                        let {x,y} = datapoint.tooltipPosition();
                                                        //console.log(" point 1 = (" + x + ", " + y );
                                                        // line
                                                        let halfWidth =  (width / 2) + 10;

                                                        const cX = chart.getDatasetMeta(0).data[0].x;
                                                        const cY = chart.getDatasetMeta(0).data[0].y;

                                                        let angle = Math.atan2( y - cY, x - cX) * 180 / Math.PI;

                                                        const distance = width * .08;

                                                        let x2 = Math.round(Math.cos(angle * Math.PI / 180) * distance + x);
                                                        let y2 = Math.round(Math.sin(angle * Math.PI / 180) * distance + y);
                                                        let textWidth = (labels[index].length * 3) + 4;
                                                        let textHt = 15;
                                                        let xFont = x2 < halfWidth ? x2 - textWidth : x2 + textWidth;

                                                        ctx.beginPath();
                                                        ctx.moveTo(x,y);
                                                        ctx.lineTo(x2,y2);
                                                        ctx.strokeStyle = 'black';
                                                        ctx.stroke();
                                                        // text
                                                        ctx.font = '600 12px sans-serif';
                                                        // position
                                                        ctx.textBaseline = 'bottom';
                                                        ctx.fillText(labels[index] , xFont , y2);
                                                        ctx.fillText(dtx[index] + '%' , xFont , y2 + textHt);
                                                    }
                                                })
                                            })
                                        }
                                    }

                                    let ${stats.subj_ctx}_data = {
                                        datasets: [{
                                            label: '${stats.subj}',
                                            data: [ ${stats.create},${stats.study},${stats.discuss},${stats.practical}],
                                            backgroundColor: [
                                                '#040FD9', // create
                                                '#021E73', // study
                                                '#040DBF', // discuss
                                                '#F20C36' // practice
                                            ],
                                            borderWidth: 0,
                                            cutout: '74%'
                                        }]
                                    }


                                    // config
                                    let ${stats.subj_ctx}_config = {
                                        type: 'doughnut',
                                        data: ${stats.subj_ctx}_data,
                                        options: {
                                            maintainAspectRation: false,
                                            offset: 4,
                                            layout: {
                                                padding: {
                                                    top: 56,
                                                    bottom: 56
                                                }
                                            },
                                            legend: {
                                                display: false
                                            },
                                            // This chart will not respond to hover, etc
                                            events: ['click']
                                        },
                                        plugins: [${stats.subj_ctx}_outer_labels, ${stats.subj_ctx}_center_label]
                                    }

                                    new Chart(${stats.subj_ctx}_ctx, ${stats.subj_ctx}_config);
                                </script>
                            </div>

                            <#-- BEGIN TESTING TYPE GAUGE -->
                            <div class="col-md-4 col-lg-3 d-flex border-lt-1 align-items-center justify-content-center">
                                <#if stats.totalPts?? && stats.totalPts != 0>
                                    <canvas id="${stats.subj_ctx + 1}"></canvas>

                                    <script>
                                        let ${stats.subj_ctx + 1}_ctx = document.getElementById('${stats.subj_ctx + 1}').getContext('2d');
                                        let ${stats.subj_ctx + 1}_center_label = {
                                            id: ' ${stats.subj_ctx + 1}_label',
                                            beforeDatasetsDraw(chart, args, pluginOptions) {
                                                const{ ctx, data } = chart;

                                                ctx.save();
                                                const x = chart.getDatasetMeta(0).data[0].x;
                                                const y = chart.getDatasetMeta(0).data[0].y;
                                                ctx.font = 'bold 16px sans-serif';
                                                ctx.fillStyle = 'rgb(0,0,0)';
                                                ctx.textAlign = 'center';
                                                ctx.textBaseline = 'middle';
                                                ctx.fillText('${stats.totalPts} pts', x, y);
                                            }
                                        }

                                        // Outer Label
                                        let ${stats.subj_ctx + 1}_outer_labels = {
                                            id: '${stats.subj_ctx + 1}_outer_labels',
                                            afterDraw(chart, args, options) {
                                                const {ctx, chartArea: { top, bottom, left, right, width, height }} = chart;
                                                const labels = ['Free Recall', 'Normal'];
                                                const dtx = [ ${stats.freeRecall},${stats.nonRPts}];

                                                chart.data.datasets.forEach((dataset, i) => {
                                                    chart.getDatasetMeta(i).data.forEach((datapoint, index) => {

                                                        if(dtx[index]) {

                                                            let {x,y} = datapoint.tooltipPosition();
                                                            //console.log(" point 1 = (" + x + ", " + y );
                                                            // line
                                                            let halfWidth =  width / 2;
                                                            //let halfHeight = ( height / 2) + 32;

                                                            const cX = chart.getDatasetMeta(0).data[0].x;
                                                            const cY = chart.getDatasetMeta(0).data[0].y;

                                                            let angle = Math.atan2( y - cY, x - cX) * 180 / Math.PI;

                                                            const distance = width * .08;

                                                            let x2 = Math.round(Math.cos(angle * Math.PI / 180) * distance + x);
                                                            let y2 = Math.round(Math.sin(angle * Math.PI / 180) * distance + y);

                                                            let textWidth = (labels[index].length * 3) + 4;
                                                            let textHt = 15;
                                                            let xFont = x2 < halfWidth ? x2 - textWidth : x2 + textWidth;

                                                            ctx.beginPath();
                                                            ctx.moveTo(x,y);
                                                            ctx.lineTo(x2,y2);
                                                            ctx.strokeStyle = 'black';
                                                            ctx.stroke();
                                                            // text
                                                            ctx.font = '600 12px sans-serif';
                                                            // position
                                                            ctx.textBaseline = 'bottom';
                                                            ctx.fillText(labels[index] , xFont , y2);
                                                            ctx.fillText(dtx[index] + 'pts' , xFont , y2 + textHt);
                                                        }
                                                    })
                                                })
                                            }
                                        }

                                        let ${stats.subj_ctx + 1}_data = {
                                            datasets: [{
                                                label: '${stats.subj}',
                                                data: [ ${stats.freeRecall},${stats.nonRPts}],
                                                backgroundColor: [
                                                    '#0CF25D', // free recall
                                                    '#0339A6', // study
                                                    '#0CF25D', // discuss
                                                    '#000000' // not used
                                                ],
                                                borderWidth: 0,
                                                cutout: '74%'
                                            }]
                                        }
                                        // Config options
                                        let ${stats.subj_ctx + 1}_config = {
                                            type: 'doughnut',
                                            data: ${stats.subj_ctx + 1}_data,
                                            options: {
                                                maintainAspectRation: false,
                                                offset: 4,
                                                layout: {
                                                    padding: {
                                                        top: 56,
                                                        bottom: 56
                                                    }
                                                },
                                                legend: {
                                                    display: false
                                                },
                                                // This chart will not respond to hover, etc
                                                events: ['click']
                                            },
                                            plugins: [${stats.subj_ctx + 1}_outer_labels, ${stats.subj_ctx + 1}_center_label]
                                        }

                                        new Chart(${stats.subj_ctx + 1}_ctx, ${stats.subj_ctx + 1}_config);
                                    </script>
                                </#if>
                            </div>
                            <hr class="mt-4 mb-5">
                        </div>
                        <#-- END GAUGES ROW -->
                    </div>
                    <!-- INSERT TABLE HERE -->
                    <div class="justify-content-center" id="${stats.subj}-table"></div>
                </#if>
            </#list>
        </div>
    </div>
</section>