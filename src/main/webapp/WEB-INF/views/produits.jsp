
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="Produits" scope="request"/>
<c:set var="active" value="produits" scope="request"/>

<%@ include file="header.jspf" %>

<div class="page-head">
    <div>
        <div class="eyebrow">GESTION DU CATALOGUE</div>
        <h1>Vos produits <span class="title-accent">✦</span></h1>
        <p>Gérez les articles, les prix et les stocks de votre boutique.</p>
    </div>

    <button type="button"
            class="btn primary"
            data-open="product-modal"
            data-new-product>
        + Nouveau produit
    </button>
</div>

<section class="panel catalog-section">
    <div class="section-top">
        <div>
            <h2>Catalogue des produits</h2>
            <p>${produits.size()} articles enregistrés</p>
        </div>

        <div class="search-field">
            <span aria-hidden="true">⌕</span>
            <input type="search"
                   placeholder="Rechercher un produit..."
                   aria-label="Rechercher un produit"
                   data-search="product-card">
        </div>
    </div>

    <div class="product-grid">
        <c:forEach var="produit" items="${produits}">
            <article class="product-card"
                     data-filter-item="product-card"
                     data-search-text="${produit.nom} ${produit.idProduit}">

                <div class="product-image">
                    <img src="${pageContext.request.contextPath}/${produit.imageUrl}"
                         alt="Illustration : ${produit.nom}"
                         loading="lazy">
                </div>

                <div class="product-info">
                    <span class="product-id">
                        REF #${produit.idProduit}
                    </span>

                    <h3><c:out value="${produit.nom}"/></h3>

                    <div class="product-meta">
                        <strong class="product-price">
                            ${produit.prix} DH
                        </strong>

                        <c:choose>
                            <c:when test="${produit.stock == 0}">
                                <span class="stock-badge out">
                                    Rupture de stock
                                </span>
                            </c:when>

                            <c:when test="${produit.stock le 5}">
                                <span class="stock-badge low">
                                    Stock faible : ${produit.stock}
                                </span>
                            </c:when>

                            <c:otherwise>
                                <span class="stock-badge available">
                                    En stock : ${produit.stock}
                                </span>
                            </c:otherwise>
                        </c:choose>
                    </div>

                    <div class="product-actions">
                        <button type="button"
                                class="btn ghost compact"
                                data-edit-product
                                data-id="${produit.idProduit}"
                                data-name="${produit.nom}"
                                data-price="${produit.prix}"
                                data-stock="${produit.stock}">
                            Modifier
                        </button>

                        <form method="post"
                              action="${pageContext.request.contextPath}/produits"
                              onsubmit="return confirm('Supprimer ce produit ?');">

                            <input type="hidden"
                                   name="action"
                                   value="delete">

                            <input type="hidden"
                                   name="id"
                                   value="${produit.idProduit}">

                            <button type="submit"
                                    class="btn danger-outline compact">
                                Supprimer
                            </button>
                        </form>
                    </div>
                </div>
            </article>
        </c:forEach>
    </div>

    <c:if test="${empty produits}">
        <p class="empty-state">
            Aucun produit pour le moment.
        </p>
    </c:if>
</section>

<div class="modal-backdrop"
     id="product-modal"
     role="dialog"
     aria-modal="true"
     aria-labelledby="product-modal-title">

    <div class="modal">
        <div class="modal-head">
            <div>
                <span class="eyebrow">CATALOGUE</span>
                <h2 id="product-modal-title">Nouveau produit</h2>
            </div>

            <button type="button"
                    class="icon-btn"
                    data-close
                    aria-label="Fermer">×</button>
        </div>

        <form method="post"
              action="${pageContext.request.contextPath}/produits"
              class="form-stack">

            <input type="hidden"
                   name="action"
                   id="product-action"
                   value="add">

            <input type="hidden"
                   name="id"
                   id="product-id">

            <label>
                Nom du produit
                <input type="text"
                       name="nom"
                       id="product-name"
                       maxlength="150"
                       required>
            </label>

            <div class="form-row">
                <label>
                    Prix (DH)
                    <input type="number"
                           name="prix"
                           id="product-price"
                           step="0.01"
                           min="0"
                           required>
                </label>

                <label>
                    Stock
                    <input type="number"
                           name="stock"
                           id="product-stock"
                           min="0"
                           required>
                </label>
            </div>

            <div class="form-actions">
                <button type="button"
                        class="btn ghost"
                        data-close>
                    Annuler
                </button>

                <button type="submit"
                        class="btn primary">
                    Enregistrer
                </button>
            </div>
        </form>
    </div>
</div>

<%@ include file="footer.jspf" %>
