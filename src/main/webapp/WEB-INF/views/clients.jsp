<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="pageTitle" value="Clients"/>
<c:set var="active" value="clients"/>
<%@ include file="header.jspf" %>

<div class="page-head">
    <div>
        <div class="eyebrow">RELATION CLIENT</div>
        <h1>Vos clients <span class="title-accent">✦</span></h1>
        <p>Retrouvez vos contacts et développez votre communauté.</p>
    </div>
    <button class="btn primary" data-open="client-modal">
        + Nouveau client
    </button>
</div>

<div class="panel">
    <div class="section-top">
        <div>
            <h2>Répertoire clients</h2>
            <p>Informations de contact et gestion des profils</p>
        </div>

        <div class="search-field">
            <span>⌕</span>
            <input type="search"
                   placeholder="Rechercher un client..."
                   data-search="client-row">
        </div>
    </div>

    <div class="table-scroll">
        <table class="data-table">
            <thead>
            <tr>
                <th>CLIENT</th>
                <th>IDENTIFIANT</th>
                <th>EMAIL</th>
                <th>TÉLÉPHONE</th>
                <th>ACTIONS</th>
            </tr>
            </thead>

            <tbody>
            <c:forEach var="c" items="${clients}">
                <tr data-filter-item="client-row"
                    data-search-text="${c.nom} ${c.email}">

                    <td>
                        <div class="client-cell">
                            <span class="avatar avatar-client">
                                ${c.nom.substring(0,1)}
                            </span>
                            <span>
                                <strong><c:out value="${c.nom}"/></strong>
                                <small>Client enregistré</small>
                            </span>
                        </div>
                    </td>

                    <td class="muted">#CL-${c.idClient}</td>
                    <td><c:out value="${c.email}"/></td>
                    <td><c:out value="${c.telephone}"/></td>

                    <td>
                        <div class="inline-actions">
                            <button class="small-action"
                                    data-edit-client
                                    data-id="${c.idClient}"
                                    data-name="${c.nom}"
                                    data-email="${c.email}"
                                    data-phone="${c.telephone}">
                                Modifier
                            </button>

                            <form method="post"
                                  action="${pageContext.request.contextPath}/clients"
                                  onsubmit="return confirm('Supprimer ce client ?');">
                                <input type="hidden" name="action" value="delete">
                                <input type="hidden" name="id" value="${c.idClient}">
                                <button class="small-action delete-link">
                                    Supprimer
                                </button>
                            </form>
                        </div>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<div class="modal-backdrop" id="client-modal">
    <div class="modal">
        <div class="modal-head">
            <div>
                <span class="eyebrow">RÉPERTOIRE</span>
                <h2 id="client-modal-title">Nouveau client</h2>
            </div>
            <button class="icon-btn" data-close>✕</button>
        </div>

        <form method="post"
              action="${pageContext.request.contextPath}/clients"
              class="form-stack">

            <input type="hidden" name="action" id="client-action" value="add">
            <input type="hidden" name="id" id="client-id">

            <label>Nom complet
                <input name="nom" id="client-name"
                       maxlength="100" required>
            </label>

            <label>Adresse email
                <input name="email" id="client-email"
                       type="email" maxlength="150" required>
            </label>

            <label>Téléphone
                <input name="telephone" id="client-phone"
                       maxlength="20">
            </label>

            <div class="form-actions">
                <button type="button" class="btn ghost" data-close>
                    Annuler
                </button>
                <button type="submit" class="btn primary">
                    Enregistrer le client
                </button>
            </div>
        </form>
    </div>
</div>

<%@ include file="footer.jspf" %>