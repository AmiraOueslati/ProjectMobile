const express = require("express");
const mongoose = require("mongoose");
const path = require("path");
// const logger = require("morgan"); // Décommentez si vous utilisez `morgan`

const app = express();
const PORT = process.env.PORT || 3000;

// Connexion à MongoDB
mongoose.connect("mongodb://localhost:27017/mydatabase")
    .then(() => console.log("MongoDB connecté"))
    .catch(err => console.log("Erreur de connexion à MongoDB :", err));

// Configurer le dossier d'images comme dossier statique accessible publiquement
app.use("/images", express.static(path.join(__dirname, "images")));

// Middleware pour traiter le JSON
app.use(express.json());
app.use(express.urlencoded({ extended: false }));

// Si `logger` est utilisé
// app.use(logger("dev"));

app.set('view engine', 'ejs');

// Importation des routes
const productRoutes = require("./routes/productRoutes"); // Vérifiez le chemin
app.use("/products", productRoutes);

const uploads = require("./routes/uploads"); // Vérifiez le chemin
app.use("/api/upload", uploads);

// Lancement du serveur
app.listen(PORT, () => {
    console.log(`Serveur démarré sur le port ${PORT}`);
});
