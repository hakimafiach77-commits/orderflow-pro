<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Commandes"/>
<c:set var="active" value="commandes"/>
<%@ include file="header.jspf" %>

<div class="page-head">
    <div>
        <div class="eyebrow">GESTION DES VENTES</div>
        <h1>Commandes <span class="title-accent">✦</span></h1>
        <p>Suivez les commandes, les clients et les articles.</p>
    </div>
    <button class="btn primary" data-open="order-modal">
        + Nouvelle commande
    </button>
</div>

<div class="orders-layout">

    <section class="panel orders-panel">
        <div class="section-top">
            <div>
                <h2>Liste des commandes</h2>
                <p>${commandes.size()} commandes enregistrées</p>
            </div>
            <div class="search-field">
                <span>⌕</span>
                <input type="search"
                       placeholder="Rechercher..."
                       data-search="order-row">
            </div>
        </div>

        <div class="table-scroll">
            <table class="data-table">
                <thead>
                <tr>
                    <th>COMMANDE</th>
                    <th>CLIENT</th>
                    <th>MONTANT</th>
                    <th>STATUT</th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="o" items="${commandes}">
                    <tr class="order-row"
                        data-filter-item="order-row"
                        data-search-text="${o.idCommande} ${o.client.nom}">

                        <td>
                            <strong class="order-id">
                                #ORD-${o.idCommande}
                            </strong>
                            <small class="table-sub">
                                ${o.dateCommande}
                            </small>
                        </td>

                        <td>
                            <div class="client-cell">
                                <span class="avatar avatar-sm">
                                    ${o.client.nom.substring(0,1)}
                                </span>
                                <c:out value="${o.client.nom}"/>
                            </div>
                        </td>

                        <td class="amount">${o.total} DH</td>

                        <td>
                            <span class="status ${o.statut == 'Livree' ? 'delivered' : o.statut == 'Annulee' ? 'cancelled' : o.statut == 'En cours' ? 'progress' : 'pending'}">
                                <c:out value="${o.statut}"/>
                            </span>
                        </td>

                        <td>
                            <a class="circle-btn tiny"
                               href="${pageContext.request.contextPath}/commandes?selected=${o.idCommande}">
                                ↗
                            </a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </section>

    <aside class="panel detail-panel">
        <c:choose>
            <c:when test="${not empty selectedCommande}">

                <div class="detail-header">
                    <div>
                        <span class="eyebrow">DÉTAILS DE LA COMMANDE</span>
                        <h2>#ORD-${selectedCommande.idCommande}</h2>
                    </div>

                    <span class="status ${selectedCommande.statut == 'Livree' ? 'delivered' : selectedCommande.statut == 'Annulee' ? 'cancelled' : selectedCommande.statut == 'En cours' ? 'progress' : 'pending'}">
                        <c:out value="${selectedCommande.statut}"/>
                    </span>
                </div>

                <div class="detail-meta">
                    <div>
                        <span>DATE</span>
                        <strong>${selectedCommande.dateCommande}</strong>
                    </div>
                    <div>
                        <span>CLIENT</span>
                        <strong>
                            <c:out value="${selectedCommande.client.nom}"/>
                        </strong>
                    </div>
                    <div>
                        <span>EMAIL</span>
                        <strong>
                            <c:out value="${selectedCommande.client.email}"/>
                        </strong>
                    </div>
                </div>

                <div class="detail-section">
                    <h3>Articles (${selectedCommande.lignes.size()})</h3>

                    <c:forEach var="l" items="${selectedCommande.lignes}">
                        <div class="order-item">
                            <img src="${l.produit.imageUrl}"
                                 alt="Produit" loading="lazy">

                            <div class="item-desc">
                                <strong>
                                    <c:out value="${l.produit.nom}"/>
                                </strong>
                                <small>
                                    ${l.quantite} × ${l.prixUnitaire} DH
                                </small>
                            </div>

                            <div class="item-end">
                                <strong>${l.sousTotal} DH</strong>

                                <form method="post"
                                      action="${pageContext.request.contextPath}/commandes"
                                      onsubmit="return confirm('Retirer cet article ?');">

                                    <input type="hidden" name="action"
                                           value="deleteLine">
                                    <input type="hidden" name="idCommande"
                                           value="${selectedCommande.idCommande}">
                                    <input type="hidden" name="idLigne"
                                           value="${l.idLigne}">

                                    <button class="mini-delete">✕</button>
                                </form>
                            </div>
                        </div>
                    </c:forEach>

                    <c:if test="${empty selectedCommande.lignes}">
                        <p class="empty-text">
                            Aucun produit dans cette commande.
                        </p>
                    </c:if>
                </div>

                <div class="detail-total">
                    <span>Total de la commande</span>
                    <strong>
                        ${selectedCommande.total} <small>DH</small>
                    </strong>
                </div>

                <div class="detail-section">
                    <h3>Ajouter un produit</h3>

                    <form method="post"
                          action="${pageContext.request.contextPath}/commandes"
                          class="form-stack compact-form">

                        <input type="hidden" name="action" value="addLine">
                        <input type="hidden" name="idCommande"
                               value="${selectedCommande.idCommande}">

                        <label>Produit
                            <select name="idProduit" required>
                                <option value="">Sélectionner un produit</option>

                                <c:forEach var="p" items="${produits}">
                                    <option value="${p.idProduit}"
                                            ${p.stock == 0 ? 'disabled' : ''}>
                                        <c:out value="${p.nom}"/>
                                        - ${p.prix} DH
                                        - stock ${p.stock}
                                    </option>
                                </c:forEach>
                            </select>
                        </label>

                        <label>Quantité
                            <input type="number" name="quantite"
                                   min="1" value="1" required>
                        </label>

                        <button class="btn primary full">
                            + Ajouter à la commande
                        </button>
                    </form>
                </div>

                <div class="detail-section">
                    <h3>Statut de la commande</h3>

                    <form method="post"
                          action="${pageContext.request.contextPath}/commandes"
                          class="form-stack compact-form">

                        <input type="hidden" name="action" value="status">
                        <input type="hidden" name="idCommande"
                               value="${selectedCommande.idCommande}">

                        <select name="statut">
                            <option value="En attente">En attente</option>
                            <option value="En cours">En cours</option>
                            <option value="Livree">Livrée</option>
                            <option value="Annulee">Annulée</option>
                        </select>

                        <button class="btn ghost full">
                            Mettre à jour le statut
                        </button>
                    </form>
                </div>

                <form method="post"
                      action="${pageContext.request.contextPath}/commandes"
                      onsubmit="return confirm('Supprimer cette commande ?');">

                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="idCommande"
                           value="${selectedCommande.idCommande}">

                    <button class="btn danger-outline full">
                        Supprimer la commande
                    </button>
                </form>

            </c:when>

            <c:otherwise>
                <div class="empty-detail">
                    Sélectionnez une commande pour voir ses détails.
                </div>
            </c:otherwise>
        </c:choose>
    </aside>
</div>

<div class="modal-backdrop" id="order-modal">
    <div class="modal">
        <div class="modal-head">
            <div>
                <span class="eyebrow">VENTES</span>
                <h2>Nouvelle commande</h2>
            </div>
            <button class="icon-btn" data-close>✕</button>
        </div>

        <form method="post"
              action="${pageContext.request.contextPath}/commandes"
              class="form-stack">

            <input type="hidden" name="action" value="add">

            <label>Client
                <select name="idClient" required>
                    <option value="">Choisir un client</option>

                    <c:forEach var="c" items="${clients}">
                        <option value="${c.idClient}">
                            <c:out value="${c.nom}"/>
                        </option>
                    </c:forEach>
                </select>
            </label>

            <p class="hint">
                La commande sera créée avec le statut « En attente ».
                Tu pourras ensuite ajouter des produits.
            </p>

            <div class="form-actions">
                <button type="button" class="btn ghost" data-close>
                    Annuler
                </button>
                <button type="submit" class="btn primary">
                    Créer la commande
                </button>
            </div>
        </form>
    </div>
</div>

<%@ include file="footer.jspf" %>