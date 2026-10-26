<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Dashboard"/>
<c:set var="active" value="dashboard"/>

<%@ include file="header.jspf" %>

<div class="page-head">
    <div>
        <div class="eyebrow">VUE D'ENSEMBLE ✦</div>
        <h1>Bonjour, Administrateur <span class="wave">✳</span></h1>
        <p>Voici ce qui se passe dans votre boutique aujourd'hui.</p>
    </div>

    <a class="btn primary"
       href="${pageContext.request.contextPath}/commandes">
        + Nouvelle commande
    </a>
</div>

<div class="metrics">

    <div class="metric">
        <div class="metric-icon mint">▤</div>
        <div class="metric-label">Commandes totales</div>
        <div class="metric-value">${ordersCount}</div>
        <div class="metric-foot">Depuis le lancement</div>
    </div>

    <div class="metric">
        <div class="metric-icon cyan">▦</div>
        <div class="metric-label">Produits actifs</div>
        <div class="metric-value">${productsCount}</div>
        <div class="metric-foot">Dans votre catalogue</div>
    </div>

    <div class="metric">
        <div class="metric-icon purple">♙</div>
        <div class="metric-label">Clients enregistrés</div>
        <div class="metric-value">${clientsCount}</div>
        <div class="metric-foot">Base de clients</div>
    </div>

    <div class="metric">
        <div class="metric-icon amber">◷</div>
        <div class="metric-label">En attente</div>
        <div class="metric-value">${pendingCount}</div>
        <div class="metric-foot">Commandes à traiter</div>
    </div>

</div>

<div class="dashboard-grid">

    <section class="panel hero-panel">
        <div class="hero-text">
            <span class="eyebrow">VOTRE ACTIVITÉ EN UN COUP D'ŒIL</span>

            <h2>
                Chaque commande compte.
                <em>Gardez une longueur d'avance.</em>
            </h2>

            <p>
                Suivez vos clients, vos produits et vos commandes
                depuis un espace unique.
            </p>

            <a class="btn primary"
               href="${pageContext.request.contextPath}/produits">
                Explorer le catalogue ↗
            </a>
        </div>

        <div class="hero-graphic">
            <div class="orbit orbit-one"></div>
            <div class="orbit orbit-two"></div>
            <span>◈</span>
        </div>
    </section>

    <section class="panel sales-panel">
        <span class="eyebrow">VALEUR DES COMMANDES</span>

        <div class="big-amount">
            ${salesTotal} <small>DH</small>
        </div>

        <p>
            Somme des lignes de commande enregistrées,
            tous statuts confondus.
        </p>

        <div class="mini-bars">
            <i style="height:36%"></i>
            <i style="height:54%"></i>
            <i style="height:44%"></i>
            <i style="height:74%"></i>
            <i style="height:59%"></i>
            <i style="height:90%"></i>
            <i style="height:68%"></i>
            <i style="height:100%"></i>
        </div>
    </section>

</div>

<section class="panel table-panel">
    <div class="section-top">
        <div>
            <h2>Commandes récentes</h2>
            <p>Les dernières transactions de votre boutique</p>
        </div>

        <a class="text-link"
           href="${pageContext.request.contextPath}/commandes">
            Voir toutes ↗
        </a>
    </div>

    <div class="table-scroll">
        <table class="data-table">
            <thead>
            <tr>
                <th>COMMANDE</th>
                <th>CLIENT</th>
                <th>DATE</th>
                <th>MONTANT</th>
                <th>STATUT</th>
                <th></th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="o" items="${commandes}">
                <tr>
                    <td>
                        <strong class="order-id">
                            #ORD-${o.idCommande}
                        </strong>
                    </td>

                    <td>
                        <span class="avatar avatar-sm">
                            ${o.client.nom.substring(0,1)}
                        </span>
                        <c:out value="${o.client.nom}"/>
                    </td>

                    <td>${o.dateCommande}</td>
                    <td class="amount">${o.total} DH</td>

                    <td>
                        <span class="status ${o.statut == 'Livree' ? 'delivered' : o.statut == 'Annulee' ? 'cancelled' : o.statut == 'En cours' ? 'progress' : 'pending'}">
                            <c:out value="${o.statut}"/>
                        </span>
                    </td>

                    <td>
                        <a class="small-action"
                           href="${pageContext.request.contextPath}/commandes?selected=${o.idCommande}">
                            Détails ↗
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</section>

<%@ include file="footer.jspf" %>