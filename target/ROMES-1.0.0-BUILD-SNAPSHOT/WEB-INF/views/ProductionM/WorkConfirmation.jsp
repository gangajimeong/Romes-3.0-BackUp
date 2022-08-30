<%@ page pageEncoding="utf-8" session="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="s"%>
<div class="card  border-secondary mr-0" id="main">
    <div class="card-header card-headerMain  border-secondary">
        <span class="badge badge-dark page-title">수주 / 작업지시 확인</span>

    </div>

    <div class="card-body  border-secondary mainPanel">
        <div class="row no-gutters">
            <div class="mt-0 table-panel col-12 border border-scondary "
                 style="padding: 1px 1px 1px 1px;">
                <div>
<%--                    <div class="input-group search-group float-right w-100 " style="padding-bottom: 10px">--%>
<%--                    </div>--%>
                    <table
                            class="table table-condensed table-striped table-bordered mb-0 table-hover "
                            id="srhistory-Table">
                        <thead id="srhistory-ColName">
                        <tr>
                            <!-- <th style="width: 9.97%;">LOTNO</th> -->
                            <th style="width: 43%;">매장명</th>
                            <th style="width: 20%;">품목</th>
                            <th style="width: 15%;">규격</th>
                            <th style="width: 15%;">수량</th>
                            <th style="width: 3%; vertical-align: middle;"><i
                                    class='bx bxs-trash'></i></th>
                        </tr>
                        </thead>
                    </table>
                </div>
                <div style="overflow: auto; height: 78vh">
                    <table
                            class="table table-condensed table-striped table-bordered mb-0 table-hover">
                        <tbody id="srhistory-table-Contents">
                        <c:forEach var="info" items="${datas}" varStatus="state">
                            <tr>
                                <td style="display: none;">${info.id}</td>
                                <td style="width: 43%;">${info.locate}</td>
                                <td style="width: 20%;">${info.product}</td>
                                <td style="width: 15%;">${info.size}</td>
                                <td style="width: 15%;">${info.count}</td>
                                <!-- <td
                                    style="width: 5%; vertical-align: middle; padding: 0px;"><button
                                        class="btn btn-light btn-outline-secondary draftBtn"
                                        style="padding-top: 0px; padding-bottom: 0px;">
                                        <i class='bx bx-edit-alt' style="font-size: 18px;"></i>
                                    </button></td> -->
                                <td style="display: none;">${info.url}</td>
                                <td style="width: 3%; vertical-align: middle;"><button
                                        class="btn btn-light btn-outline-secondary p-0 delete_work_order">
                                    <i class='bx bxs-trash' style="font-size: 18px;"></i>
                                </button></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>

            </div>

        </div>
    </div>
</div>