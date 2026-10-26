(() => {

    const $ = selector => document.querySelector(selector);

    // Ouvrir une fenêtre modale
    const show = id => {
        const modal = $("#" + id);

        if (modal) {
            modal.classList.add("show");
            document.body.classList.add("modal-open");
        }
    };

    // Fermer les fenêtres modales
    const hide = () => {
        document.querySelectorAll(".modal-backdrop.show")
            .forEach(modal => modal.classList.remove("show"));

        document.body.classList.remove("modal-open");
    };

    // Boutons d'ouverture
    document.querySelectorAll("[data-open]").forEach(button => {
        button.addEventListener("click", () => {
            show(button.dataset.open);
        });
    });

    // Boutons de fermeture
    document.querySelectorAll("[data-close]").forEach(button => {
        button.addEventListener("click", hide);
    });

    // Fermer en cliquant sur l'arrière-plan
    document.querySelectorAll(".modal-backdrop").forEach(modal => {
        modal.addEventListener("click", event => {
            if (event.target === modal) {
                hide();
            }
        });
    });

    // Fermer avec Échap
    document.addEventListener("keydown", event => {
        if (event.key === "Escape") {
            hide();
        }
    });

    // Recherche dans les tableaux et les cartes
    document.querySelectorAll("[data-search]").forEach(input => {

        input.addEventListener("input", () => {

            const query = input.value.toLocaleLowerCase().trim();

            document.querySelectorAll(
                '[data-filter-item="' + input.dataset.search + '"]'
            ).forEach(element => {

                const text = (element.dataset.searchText || "")
                    .toLocaleLowerCase();

                element.hidden = !text.includes(query);
            });
        });
    });

    // Modifier un produit
    document.querySelectorAll("[data-edit-product]").forEach(button => {

        button.addEventListener("click", () => {

            $("#product-modal-title").textContent = "Modifier le produit";
            $("#product-action").value = "update";
            $("#product-id").value = button.dataset.id;
            $("#product-name").value = button.dataset.name;
            $("#product-price").value = button.dataset.price;
            $("#product-stock").value = button.dataset.stock;

            show("product-modal");
        });
    });

    // Modifier un client
    document.querySelectorAll("[data-edit-client]").forEach(button => {

        button.addEventListener("click", () => {

            $("#client-modal-title").textContent = "Modifier le client";
            $("#client-action").value = "update";
            $("#client-id").value = button.dataset.id;
            $("#client-name").value = button.dataset.name;
            $("#client-email").value = button.dataset.email;
            $("#client-phone").value = button.dataset.phone;

            show("client-modal");
        });
    });

})();