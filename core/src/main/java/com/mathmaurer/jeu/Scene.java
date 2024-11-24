package com.mathmaurer.jeu;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mathmaurer.affichage.CompteARebours;
import com.mathmaurer.affichage.Score;
import com.mathmaurer.objets.Block;
import com.mathmaurer.objets.Objet;
import com.mathmaurer.objets.Piece;
import com.mathmaurer.objets.TuyauRouge;
import com.mathmaurer.personnages.Champ;
import com.mathmaurer.personnages.Mario;
import com.mathmaurer.personnages.Tortue;


public class Scene implements Screen {
    //**** Variables ****//
    private Texture imgFond1, imgFond2, imgChateau1, imgDepart, imgChateauFin, imgDrapeau;
    private SpriteBatch batch;

    private int xFond1, xFond2, dx, xPos;
    private final int VITESSE_FOND = 4; // Vitesse de déplacement de l'écran
    private final int VITESSE_MARIO = 2; // Vitesse de déplacement de Mario
    private final int largeurFond = 700;
    private int positionLimiteDepart;
    private boolean aDeplaceDroite;
    private boolean departAtteint;

    private Mario mario;
    private List<TuyauRouge> tuyauxRouges;
    private List<Block> blocs;
    private List<Champ> champs;
    private List<Tortue> tortues;
    private List<Piece> pieces; // Nouvelle liste pour les pièces
    private Score scoreManager; // Ajout du gestionnaire de score
    private CompteARebours compteARebours;

    private boolean isInMenu;
    private Stage stage;
    private Table menuTable;
    private BitmapFont font;
    private String[] menuOptions = {"JOUER", "OPTIONS", "QUITTER"};
    private Label[] menuLabels;
    private int currentMenuOption;
    private Texture menuBackground;
    private InputAdapter menuInputProcessor;
    private InputAdapter gameInputProcessor;

    private Sound sound;
    private Music backgroundMusic;
    private Sound jumpSound;
    private Sound coinSound;
    private Sound dieSound;
    private Sound powerUpSound;
    private Sound powerDownSound;
    private boolean isMusicEnabled = true;
    private float musicVolume = 0.5f;
    private float soundVolume = 1.0f;

    private final int HAUTEUR_SOL = 55; // Sol à y = 0
    private final int HAUTEUR_PLAFOND = 300; // Par exemple, plafond à y = 300

    private float chateauFinX; // Position initiale du château de fin
    private float drapeauX;    // Position initiale du drapeau

        // Ajoutez ces variables en haut de votre classe Scene
    private boolean afficherMessageFin = false;
    private float timerFinPartie = 0;
    private final float DELAI_MENU_FIN = 9f; // 3 secondes de délai
    private boolean musicFinJouee = false;
    
  
    private static final int BACKGROUND_LAYERS = 1; // Change to 1 layer
   private static final float[] PARALLAX_SPEEDS = {1.0f}; // Single speed


    private Texture[] backgroundLayers;
    private float[] backgroundPositions;
    private float worldPosition;
    private boolean isBackgroundLoaded;
    private final float virtualWidth;  // Screen/viewport width


    private Texture backgroundTexture;
    private float[] positions;  // Stores x positions of background instances
    private float accumulatedError;
    
    private static final float SCROLL_SPEED = 1.0f;  // Adjust as needed
    private static final float BUFFER_ZONE = 100f;   // Off-screen buffer for smooth scrolling
    


    private static final int POSITION_CHATEAU_DEBUT = 10;  // Position X du château de début
    private static final int POSITION_PANNEAU_DEPART = 200; // Position X du panneau de départ
    private static final int POSITION_CHATEAU_FIN = 5000;  // Position X du château de fin
    private static final int POSITION_DRAPEAU = 4950;      // Position X du drapeau
    private static final int HAUTEUR_OBJETS = 60;  
    

    private static final float VITESSE_DEFILEMENT = 2.0f;  // Multiplicateur de vitesse
    private static final float SEUIL_REPOSITION = -700.0f; // Seuil de repositionnement (largeurFond)
    private float accumulateurDeplacement = 0.0f;  

// Déclarations globales
private float timer = 0; // Temps écoulé depuis le début du jeu
private static final float DELAY = 5.0f; // Temps en secondes avant disparition
private boolean afficherElementsDebut = true;

private boolean showWelcomeMessage = true;  // Flag pour afficher le message de bienvenue une seule fois

// Constructor to initialize virtualWidth
public Scene(float screenWidth) {
    this.virtualWidth = screenWidth;
    initializeBackground();
}
    public Scene() {
        // Initialisation des positions
        this.xFond1 = 0;
        this.xFond2 = largeurFond;
        this.dx = 0;
        this.xPos = 200;
        this.aDeplaceDroite = false;
        this.departAtteint = false;
        this.chateauFinX = 4700;
        this.drapeauX = 4400;
        this.xPos = positionLimiteDepart;

        isInMenu = true;
        stage = new Stage(new ScreenViewport());
        font = new BitmapFont();
        menuBackground = new Texture("images/fondEcran.png"); 

        // Chargement des images
        imgFond1 = new Texture("images/fondEcran.png");
        imgFond2 = new Texture("images/fondEcran.png");
        imgChateau1 = new Texture("images/chateau1.png");
        imgDepart = new Texture("images/depart.png");
        imgChateauFin = new Texture("images/chateauFin.png");
        imgDrapeau = new Texture("images/drapeau.png");

        initializeInputProcessors();
        // Création du menu
        createMenu();
        initializeAudio();


        batch = new SpriteBatch();

        // Initialisation des objets
        mario = new Mario(200, 55, 28, 50); // Initialisation avec la position de départ
        // mario = new Mario(Gdx.graphics.getWidth() / 2 - 21.5f, 55, 43, 65);
        // Initialisation des tuyaux et des blocs
        tuyauxRouges = new ArrayList<>();
        blocs = new ArrayList<>();
        champs = new ArrayList<>();
        tortues = new ArrayList<>();
        pieces = new ArrayList<>();
        scoreManager = new Score();
        compteARebours = new CompteARebours();

       // Ajouter des tuyaux rouges
        tuyauxRouges.add(new TuyauRouge(600, 55));
        tuyauxRouges.add(new TuyauRouge(1000, 55));
        tuyauxRouges.add(new TuyauRouge(1600, 55));
        tuyauxRouges.add(new TuyauRouge(1900, 55));
        tuyauxRouges.add(new TuyauRouge(2500, 55));
        tuyauxRouges.add(new TuyauRouge(3000, 55));
        tuyauxRouges.add(new TuyauRouge(3400, 55)); // Ajout pour plus de régularité
        tuyauxRouges.add(new TuyauRouge(4000, 55));

        // Ajouter des blocs (hauteurs adaptées)
        blocs.add(new Block(500, 120)); // Un bloc bas pour apprendre à sauter
        blocs.add(new Block(800, 150)); // Accessible par un saut simple
        blocs.add(new Block(1200, 150));
        blocs.add(new Block(1270, 150)); // Petits espaces entre blocs
        blocs.add(new Block(1340, 150));
        blocs.add(new Block(2000, 120)); // Bloc plus bas pour éviter la monotonie
        blocs.add(new Block(2600, 130)); // Légèrement plus haut mais accessible
        blocs.add(new Block(2650, 150));
        blocs.add(new Block(3000, 120)); // Ajouter de la variété dans les hauteurs
        blocs.add(new Block(3500, 150));
        blocs.add(new Block(3700, 130)); // Bloc isolé pour un challenge

        // Ajouter des champs (espacement raisonnable pour laisser le joueur respirer)
        champs.add(new Champ(900, 55));
        champs.add(new Champ(1400, 55));
        champs.add(new Champ(2100, 55));
        champs.add(new Champ(2400, 55));
        champs.add(new Champ(3100, 55));
        champs.add(new Champ(3500, 55));

        // Ajouter des tortues (placées entre les tuyaux pour éviter les zones vides)
        tortues.add(new Tortue(950, 55));
        tortues.add(new Tortue(1500, 55));
        tortues.add(new Tortue(1800, 55));
        tortues.add(new Tortue(2200, 55)); // Plus proche pour augmenter la difficulté
        tortues.add(new Tortue(2900, 55));
        tortues.add(new Tortue(3300, 55));
        tortues.add(new Tortue(3900, 55));


        // Position limite de départ pour Mario
        positionLimiteDepart = 300;  
        xPos = positionLimiteDepart;     
        


        
        // Ajouter des pièces à des positions spécifiques
        pieces.add(new Piece(450, 180,"images/java.png"));
        pieces.add(new Piece(480, 180,"images/phython.jpeg"));
        pieces.add(new Piece(510, 180, "images/csharp.png"));
        pieces.add(new Piece(1250, 200, "images/ruby.jpeg"));
        pieces.add(new Piece(1280, 200, "images/html.png"));
        pieces.add(new Piece(1310, 200, "images/css.png"));
        pieces.add(new Piece(2050, 190, "images/javascript.png"));
        pieces.add(new Piece(2080, 190, "images/php.jpeg"));
        pieces.add(new Piece(2680, 220, "images/laravel.png"));
        pieces.add(new Piece(3550, 180, "images/react.png"));
        pieces.add(new Piece(3580, 180, "images/sql.png"));
      

    for (Piece piece : pieces) {
        new Thread(piece).start();
    }

    this.virtualWidth = 800f;  // Default width, adjust as needed
}

       
    
    private void initializeBackground() {
        try {
            // Load single background texture
            backgroundTexture = new Texture("images/fondEcran.png");
            backgroundTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
            
            // Initialize two background positions for seamless scrolling
            positions = new float[2];
            positions[0] = 0;
            positions[1] = backgroundTexture.getWidth();
            
            isBackgroundLoaded = true;
        } catch (Exception e) {
            System.err.println("Error loading background texture: " + e.getMessage());
            isBackgroundLoaded = false;
        }
    }
    
    // Update background positions
    private void updateBackground(float delta, float dx) {
        if (!isBackgroundLoaded || dx == 0) return;
        
        // Calculate movement
        float movement = dx * SCROLL_SPEED * delta;
        worldPosition += movement;
        accumulatedError += movement;
        
        // Update positions
        for (int i = 0; i < positions.length; i++) {
            positions[i] -= movement;
        }
        
        float width = backgroundTexture.getWidth();
        
        // Handle wrapping for both directions
        if (dx > 0) {  // Moving right
            for (int i = 0; i < positions.length; i++) {
                if (positions[i] <= -width) {
                    positions[i] = findFurthestPosition() + width;
                }
            }
        } else {  // Moving left
            for (int i = 0; i < positions.length; i++) {
                if (positions[i] >= virtualWidth) {
                    positions[i] = findNearestPosition() - width;
                }
            }
        }
        
        // Error correction to prevent floating-point drift
        if (Math.abs(accumulatedError) > 1.0f) {
            for (int i = 0; i < positions.length; i++) {
                positions[i] = Math.round(positions[i]);
            }
            accumulatedError = 0;
        }
    }
    
    // Helper method to find furthest background position
    private float findFurthestPosition() {
        float max = Float.NEGATIVE_INFINITY;
        for (float pos : positions) {
            if (pos > max) max = pos;
        }
        return max;
    }
    
    // Helper method to find nearest background position
    private float findNearestPosition() {
        float min = Float.POSITIVE_INFINITY;
        for (float pos : positions) {
            if (pos < min) min = pos;
        }
        return min;
    }
    
    // Render background
    private void renderBackground(SpriteBatch batch) {
        if (!isBackgroundLoaded) return;
        
        // Render background instances
        for (float position : positions) {
            // Only render if within visible area plus buffer
            if (position > -backgroundTexture.getWidth() - BUFFER_ZONE && 
                position < virtualWidth + BUFFER_ZONE) {
                batch.draw(backgroundTexture, position, 0);
            }
        }
    }
    
    // Reset background positions if needed
    public void resetBackground() {
        if (!isBackgroundLoaded) return;
        positions[0] = 0;
        positions[1] = backgroundTexture.getWidth();
        worldPosition = 0;
        accumulatedError = 0;
    }

    private void initializeAudio() {
        try {
            // Vérifiez si le fichier audio de fond existe
            if (Gdx.files.internal("audio/baground.mp3").exists()) {
                backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/baground.mp3"));
                backgroundMusic.setLooping(true); // Si le fichier est trouvé, on le met en boucle
                backgroundMusic.play();
                System.out.println("Musique de fond chargée avec succès !");
            } else {
                System.err.println("Erreur : fichier audio background_music.mp3 introuvable !");
            }
    
            // Charger le son du saut
            if (Gdx.files.internal("audio/saut.wav").exists()) {
                jumpSound = Gdx.audio.newSound(Gdx.files.internal("audio/saut.wav"));
            } else {
                System.err.println("Erreur : fichier audio saut.wav introuvable !");
            }
    
        } catch (Exception e) {
            System.err.println("Erreur lors de l'initialisation des sons : " + e.getMessage());
            e.printStackTrace();
        }
    
    
    
        
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(musicVolume);

        // Charger les effets sonores
        jumpSound = Gdx.audio.newSound(Gdx.files.internal("audio/saut.wav"));
        coinSound = Gdx.audio.newSound(Gdx.files.internal("audio/piece.wav"));
        dieSound = Gdx.audio.newSound(Gdx.files.internal("audio/partiePerdue.wav"));
        powerUpSound = Gdx.audio.newSound(Gdx.files.internal("audio/partieGagnee.wav"));
        powerDownSound = Gdx.audio.newSound(Gdx.files.internal("audio/boum.wav"));
    }


    private void initializeInputProcessors() {
        // Processeur d'entrée pour le menu
        menuInputProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (isInMenu) {
                    switch (keycode) {
                        case Input.Keys.UP:
                            playSound(coinSound);
                            navigateMenu(-1);
                            return true;
                        case Input.Keys.DOWN:
                            playSound(coinSound);
                            navigateMenu(1);
                            return true;
                        case Input.Keys.ENTER:
                            playSound(powerUpSound);
                            selectMenuOption();
                            return true;
                        case Input.Keys.M:
                            toggleMusic();
                            return true;
                    }
                }
                return false;
            }
        };
    
        // Processeur d'entrée pour le jeu
        gameInputProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                if (!isInMenu && mario.isVivant()) {
                    switch (keycode) {
                        case Input.Keys.RIGHT:
                            if (xPos == -1) {
                                xPos = 0;
                                xFond1 = -50;
                                xFond2 = 750;
                            }
                            mario.setMarche(true);
                            mario.setVersDroite(true);
                            dx = VITESSE_FOND;
                            return true;
    
                        case Input.Keys.LEFT:
                            if (xPos == 4431) {
                                xPos = 4430;
                                xFond1 = -50;
                                xFond2 = 750;
                            }
                            mario.setMarche(true);
                            mario.setVersDroite(false);
                            dx = -VITESSE_FOND;
                            return true;
    
                        case Input.Keys.SPACE:
                            playSound(jumpSound);
                            mario.jump(HAUTEUR_SOL);
                            return true;
    
                        case Input.Keys.UP:
                            mario.climb(true, HAUTEUR_SOL, HAUTEUR_PLAFOND);
                            return true;
                            
                        case Input.Keys.M:
                            toggleMusic();
                            return true;
                    }
                }
                return false;
            }
    
            @Override
            public boolean keyUp(int keycode) {
                if (!isInMenu && mario.isVivant()) {
                    if (keycode == Input.Keys.RIGHT || keycode == Input.Keys.LEFT) {
                        mario.setMarche(false);
                        dx = 0;
                        return true;
                    }
                }
                return false;
            }
        };
    
        // Initialiser avec le processeur du menu par défaut
        Gdx.input.setInputProcessor(menuInputProcessor);
    }

    public void create() {
        font = new BitmapFont();  // Initialisation de la police
    }
    
    public boolean isInMenu() {
        return isInMenu;
    }
    
    private void createMenu() {
        menuTable = new Table();
        menuTable.setFillParent(true);
        menuLabels = new Label[menuOptions.length];

        for (int i = 0; i < menuOptions.length; i++) {
            Label.LabelStyle labelStyle = new Label.LabelStyle();
            labelStyle.font = font;
            menuLabels[i] = new Label(menuOptions[i], labelStyle);
            menuTable.add(menuLabels[i]).pad(10).row();
        }

        stage.addActor(menuTable);
        updateMenuHighlight();
    }

    private void updateMenuHighlight() {
        for (int i = 0; i < menuLabels.length; i++) {
            menuLabels[i].setColor(i == currentMenuOption ? 1 : 1, 1, i == currentMenuOption ? 0 : 1, 1);
        }
    }

    public void navigateMenu(int direction) {
        currentMenuOption = (currentMenuOption + direction + menuOptions.length) % menuOptions.length;
        updateMenuHighlight();
    }

    public void selectMenuOption() {
        switch (currentMenuOption) {
            case 0:
                startGame();
                break;
            case 1:
                showOptions();
                break;
            case 2:
                Gdx.app.exit();
                break;
        }
    }

    
    private void startGame() {
        // Désactive le menu
        isInMenu = false;
        
        // Configure le processeur d'entrées pour le jeu
        Gdx.input.setInputProcessor(gameInputProcessor);
        
        // Démarre la musique si elle est activée
        if (isMusicEnabled) {
            backgroundMusic.play();
        }
        
        // Initialise l'état de Mario
        mario.setMarche(false);  // Mario ne marche pas au départ
        dx = 0;  // Vitesse horizontale à 0
        
        // Réinitialise les variables de fin de partie
        afficherMessageFin = false;  // Pas de message de fin
        timerFinPartie = 0;  // Remise à zéro du timer de fin
        musicFinJouee = false;  // La musique de fin n'a pas été jouée
        
        
        // Crée de nouvelles instances pour le score et le compte à rebours
        scoreManager = new Score();
        compteARebours = new CompteARebours();
        
        // Positionne Mario à sa position initiale
        mario.setX(200);  // Position X initiale
        mario.setY(55);   // Position Y initiale
        mario.setVivant(true);  // Mario est vivant

        showWelcomeMessage = true;
    }
    
    
    private void showOptions() {
        System.out.println("Options menu clicked");
        // Implémentez votre logique d'options ici
    }

    private boolean partieGagnee() {
        return this.compteARebours.getCompteurTemps() > 0 && 
               this.mario.isVivant() &&  
               this.scoreManager.getScore() >= 50 && 
               this.xPos > 4500;
    }
    
    private boolean partiePerdue() {
        return !this.mario.isVivant() || 
               this.compteARebours.getCompteurTemps() <= 0;
    }
    
    public boolean finDePartie() {
        return this.partieGagnee() || this.partiePerdue();
    }
    

    @Override
    public void render(float delta) {
        // Gdx.gl.glClearColor(0, 0, 0, 1);
        // Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (isInMenu) {
            renderMenu();  // Afficher le menu si on est dans le menu
        } else {
            renderGame(delta);  // Afficher le jeu si on n'est pas dans le menu
        }
    
        // Afficher le message de bienvenue une seule fois
        if (showWelcomeMessage) {
            System.out.println("Message de bienvenue affiché");  // Ajout d'un message de débogage
            showWelcomeMessage();  // Afficher le message de bienvenue
            showWelcomeMessage = false;  // Désactiver l'affichage du message après l'avoir montré
        }
    }
    
    
    private void showWelcomeMessage() {
        batch.begin();
        font.draw(batch, "Bienvenue dans le jeu, Mario !", 100, Gdx.graphics.getHeight() / 2);
        batch.end();
    }
    

    private void renderMenu() {
        batch.begin();
        batch.draw(menuBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        stage.act();
        stage.draw();
    }



    public void renderGame(float delta) {
        // Mise à jour du défilement des fonds
        updateBackground(delta, dx);
        
        // Mise à jour du timer
        timer += delta; // Ajoute le temps écoulé depuis le dernier frame
        
        // Désactiver les éléments de début après le délai spécifié
        if (timer > DELAY) {
            afficherElementsDebut = false;
        }
        
        // Liste des objets pour Mario
        List<Objet> objets = new ArrayList<>();
        objets.addAll(tuyauxRouges);
        objets.addAll(blocs);
        mario.update(HAUTEUR_SOL, objets);
        
        // Déplacement de Mario et des objets
        deplacementMario();
        deplacementFond();
        deplacementObjets();
        
        // Gérer les collisions
        gererCollisions();
        
        // Effacer l'écran avant de dessiner
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        // Démarrer le batch pour dessiner les éléments
        batch.begin();
        renderBackground(batch);
        
        // Dessiner les fonds
        batch.draw(imgFond1, xFond1, 0);
        batch.draw(imgFond2, xFond2, 0);
        
//          // Dessiner le château de départ
//     if (xPos <= positionLimiteDepart + 50) {  // Ajouter une marge pour la visibilité
//         batch.draw(imgChateau1, 10 - dx, 55);  // Ajuster la position en fonction du déplacement
//         batch.draw(imgDepart, 220 - dx, 55);
//     }
    
//    // Dessiner le château de fin et le drapeau
//     if (xPos >= 4000) {  // Ajuster cette valeur selon vos besoins
//         batch.draw(imgChateauFin, chateauFinX, 55);
//         batch.draw(imgDrapeau, drapeauX, 55);
//     }

    
        // Vérifier si les éléments de début doivent être affichés
        if (afficherElementsDebut) {
            // Calculer les positions relatives des éléments (château et panneau de départ)
            int positionChateauDebutX = POSITION_CHATEAU_DEBUT + xFond1;
            int positionPanneauDepartX = POSITION_PANNEAU_DEPART + xFond1;
        
            // Dessiner le château s'il est visible à l'écran
            if (positionChateauDebutX > -imgChateau1.getWidth() && 
                positionChateauDebutX < Gdx.graphics.getWidth()) {
                batch.draw(imgChateau1, positionChateauDebutX, HAUTEUR_OBJETS);
            }
        
            // Dessiner le panneau de départ s'il est visible à l'écran
            if (positionPanneauDepartX > -imgDepart.getWidth() && 
                positionPanneauDepartX < Gdx.graphics.getWidth()) {
                batch.draw(imgDepart, positionPanneauDepartX, HAUTEUR_OBJETS);
            }
        }
        
        // Dessiner Mario
        mario.dessine(batch);
        
        // Dessiner les tuyaux rouges
        for (TuyauRouge tuyau : tuyauxRouges) {
            batch.draw(tuyau.getTextureObjet(), tuyau.getX(), tuyau.getY());
        }
        
        // Dessiner les blocs
        for (Block bloc : blocs) {
            batch.draw(bloc.getTextureObjet(), bloc.getX(), bloc.getY());
        }
        
        // Dessiner les autres éléments (champs, tortues, pièces, etc.)
        for (Champ champ : champs) {
            champ.render(batch);
        }
        
        for (Tortue tortue : tortues) {
            tortue.render(batch);
        }
    
        // Dessiner les pièces avec animation
        for (Piece piece : pieces) {
            piece.render(batch);
    }
    
    // Dessiner le score en dernier pour qu'il soit au-dessus de tout
        scoreManager.render(batch);
    
        // Dessiner le compte à rebours
        compteARebours.render(batch);
    

       // Gestion de la fin de partie


// Modifiez la partie du code de rendu comme ceci
if (finDePartie()) {
    // Gestion du timer et de l'affichage
    timerFinPartie += Gdx.graphics.getDeltaTime();
    afficherMessageFin = true;

    BitmapFont policeFin = new BitmapFont();
    policeFin.getData().setScale(2.0f);
    // policeFin.setColor(Color.RED);

    if (partieGagnee()) {
        // Jouer la musique de victoire une seule fois
        if (!musicFinJouee) {
            backgroundMusic.stop();
            playSound(powerUpSound);
            musicFinJouee = true;
        }
        policeFin.draw(batch, "Vous avez toutes les compétences !!", 120, 180);
    } else {
        // Jouer la musique de défaite une seule fois
        if (!musicFinJouee) {
            backgroundMusic.stop();
            playSound(dieSound);
            musicFinJouee = true;
        }
        policeFin.draw(batch, "Vous ne passez pas le test...", 120, 180);
    }

    // Dessiner le château de fin et le drapeau
    batch.draw(imgChateauFin, chateauFinX, 55);
    batch.draw(imgDrapeau, drapeauX, 55);

    // Attendre le délai avant d'afficher le menu
    if (timerFinPartie >= DELAI_MENU_FIN) {
        afficherMessageFin = false;
        timerFinPartie = 0;
        musicFinJouee = false;
        isInMenu = true;
        Gdx.input.setInputProcessor(menuInputProcessor);
        // Réinitialiser les variables pour la prochaine partie
     
    }
}
        // Terminer le batch une seule fois à la fin
        batch.end();
    }
    
    
    

    // Getters et Setters
    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getxFond1() {
        return xFond1;
    }

    public void setxFond1(int xFond1) {
        this.xFond1 = xFond1;
    }

    public int getxFond2() {
        return xFond2;
    }

    public void setxFond2(int xFond2) {
        this.xFond2 = xFond2;
    }

    /**
     * Gère le déplacement parallaxe du fond avec optimisation des performances
     * Utilise un accumulateur pour les petits déplacements et évite les calculs inutiles
     */
    private void deplacementFond() {
        // Pas de déplacement si la vitesse horizontale est nulle
        if (dx == 0) return;
    
        // Calculer le déplacement total basé sur la vitesse
        float deplacement = dx * VITESSE_DEFILEMENT;
        accumulateurDeplacement += deplacement;
    
        // Appliquer le déplacement aux positions des fonds
        xFond1 -= deplacement;
        xFond2 -= deplacement;
    
        // Gestion du repositionnement si les fonds sortent de l'écran
        if (dx > 0) { // Déplacement vers la droite
            if (xFond1 <= SEUIL_REPOSITION) {
                xFond1 = xFond2 + largeurFond;
                accumulateurDeplacement = 0; // Réinitialiser pour éviter les erreurs
            }
            if (xFond2 <= SEUIL_REPOSITION) {
                xFond2 = xFond1 + largeurFond;
                accumulateurDeplacement = 0;
            }
        } else { // Déplacement vers la gauche
            if (xFond1 >= largeurFond) {
                xFond1 = xFond2 - largeurFond;
                accumulateurDeplacement = 0;
            }
            if (xFond2 >= largeurFond) {
                xFond2 = xFond1 - largeurFond;
                accumulateurDeplacement = 0;
            }
        }
    
        // Correction périodique pour éviter une accumulation d'erreurs
        if (Math.abs(accumulateurDeplacement) > 1.0f) {
            xFond1 = Math.round(xFond1);
            xFond2 = Math.round(xFond2);
            accumulateurDeplacement = 0;
        }
    }
    
    // Réinitialisation des positions des fonds, utile pour recommencer un niveau
    public void resetFondPositions() {
        xFond1 = 0;
        xFond2 = largeurFond;
        accumulateurDeplacement = 0;
    }
    

    private void deplacementMario() {
        // Mise à jour de la position globale
        if (dx != 0) {
            xPos += dx * VITESSE_MARIO;
        }
        
        // Limites de déplacement
        if (xPos < positionLimiteDepart) {
            xPos = positionLimiteDepart;
        }
        
        // Position de Mario à l'écran
        mario.setX(Gdx.graphics.getWidth() / 2 - mario.getLargeur() / 2);
    }
    private void deplacementObjets() {
        // Déplacer les tuyaux rouges
        for (TuyauRouge tuyau : tuyauxRouges) {
            tuyau.setX(tuyau.getX() - dx * 2); // Déplacement plus rapide des objets
        }

        // Déplacer les blocs
        for (Block bloc : blocs) {
            bloc.setX(bloc.getX() - dx * 2); // Déplacement plus rapide des objets
        }

        // Déplacer les champs
        for (Champ champ : champs) {
            champ.setX(champ.getX() - dx * 2); // Déplacement plus rapide des objets
        }

        // Déplacer les tortues
        for (Tortue tortue : tortues) {
            tortue.setX(tortue.getX() - dx * 2); // Déplacement plus rapide des objets
        }

        for (Piece piece : pieces) {
            piece.setX(piece.getX() - dx * 2);
        }

        chateauFinX -= dx * 2;
        drapeauX -= dx * 2;
    }

    private void gererCollisions() {
        // Gérer les collisions entre Mario et les tuyaux rouges
        for (TuyauRouge tuyau : tuyauxRouges) {
            if (mario.proche(tuyau)) {
                if (mario.contactDessus(tuyau)) {
                    // Mario se tient sur le tuyau
                    mario.setY(tuyau.getY() + tuyau.getHauteur());
                    mario.setJumping(false); // Mario n'est plus en train de sauter
                } else if (mario.contactAvant(tuyau) || mario.contactArriere(tuyau) || mario.contactDessous(tuyau)) {
                    // Gérer la collision (par exemple, arrêter le mouvement de Mario)
                    mario.setMarche(false);
                    dx = 0;
                }
            }
        }
    
        // Gérer les collisions entre Mario et les blocs
        for (Block bloc : blocs) {
            if (mario.proche(bloc)) {
                if (mario.contactDessus(bloc)) {
                    // Mario se tient sur le bloc
                    mario.setY(bloc.getY() + bloc.getHauteur());
                    mario.setJumping(false); // Mario n'est plus en train de sauter
                } else if (mario.contactAvant(bloc) || mario.contactArriere(bloc) || mario.contactDessous(bloc)) {
                    // Gérer la collision (par exemple, arrêter le mouvement de Mario)
                    mario.setMarche(false);
                    dx = 0;
                }
            }
        }
    
        // Gérer les collisions entre Mario et les pièces
        Iterator<Piece> iterPieces = pieces.iterator();
        while (iterPieces.hasNext()) {
            Piece piece = iterPieces.next();
            if (mario.proche(piece)) {
                if (mario.contactAvant(piece) || mario.contactArriere(piece) || mario.contactDessus(piece) || mario.contactDessous(piece)) {
                    // Jouer le son de collection de pièce
                    playSound(coinSound);
                    // Augmenter le score
                    scoreManager.addScore(10);
                    // Retirer la pièce
                    piece.stopAnimation();
                    iterPieces.remove();
                }
            }
        }
    
        // Gérer les collisions entre Mario et les champs
        for (Champ champ : champs) {
            if (mario.proche(champ) && champ.isVivant()) {
                if (mario.contactDessus(champ)) {
                    playSound(powerDownSound);
                    champ.meurt(); // Mario écrase le champ
                    mario.setVelocityY(-10); // Petit saut après avoir écrasé un ennemi
                } else if (mario.contactAvant(champ) || mario.contactArriere(champ) || mario.contactDessous(champ)) {
                    if (mario.isVivant()) {
                        playSound(dieSound);
                        mario.meurt();
                    }
                }
            }
        }
    
        // Gérer les collisions entre Mario et les tortues
        for (Tortue tortue : tortues) {
            if (mario.proche(tortue) && tortue.isVivant()) {
                if (mario.contactDessus(tortue)) {
                    playSound(powerDownSound);
                    tortue.mourir(); // Mario écrase la tortue
                    mario.setVelocityY(-10); // Petit saut après avoir écrasé un ennemi
                } else if (mario.contactAvant(tortue) || mario.contactArriere(tortue) || mario.contactDessous(tortue)) {
                    if (mario.isVivant()) {
                        playSound(dieSound);
                        mario.meurt();
                    }
                }
            }
        }
    
        // Gérer les collisions entre les champignons et les objets
        for (Champ champ : champs) {
            for (Objet objet : tuyauxRouges) {
                if (champ.proche(objet)) {
                    champ.contact(objet);
                }
            }
            for (Objet objet : blocs) {
                if (champ.proche(objet)) {
                    champ.contact(objet);
                }
            }
        }
    
        // Gérer les collisions entre les tortues et les objets
        for (Tortue tortue : tortues) {
            for (Objet objet : tuyauxRouges) {
                if (tortue.proche(objet)) {
                    tortue.contact(objet);
                }
            }
            for (Objet objet : blocs) {
                if (tortue.proche(objet)) {
                    tortue.contact(objet);
                }
            }
        }
    }
    
    private void toggleMusic() {
        isMusicEnabled = !isMusicEnabled;
        if (isMusicEnabled) {
            backgroundMusic.play();
        } else {
            backgroundMusic.pause();
        }
    }

    private void playSound(Sound sound) {
        if (sound != null) {
            sound.play(soundVolume);
        }
    }


    @Override
    public void show() {
    
    }

    @Override
    public void resize(int width, int height) {}

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        batch.dispose();
        imgFond1.dispose();
        imgFond2.dispose();
        imgChateau1.dispose();
        imgDepart.dispose();
        imgChateauFin.dispose();
        imgDrapeau.dispose();
        for (TuyauRouge tuyau : tuyauxRouges) {
            tuyau.dispose();
        }
        for (Block bloc : blocs) {
            bloc.dispose();
        }
        for (Champ champ : champs) {
            champ.dispose();
        }
        for (Tortue tortue : tortues) {
            tortue.dispose();
        }
        backgroundMusic.dispose();
        jumpSound.dispose();
        coinSound.dispose();
        dieSound.dispose();
        powerUpSound.dispose();
        powerDownSound.dispose();

        for (Piece piece : pieces) {
            piece.dispose();
        }

        scoreManager.dispose(); // Ajouter la libération des ressources du score
        if (backgroundTexture != null) {
            backgroundTexture.dispose();
        }
        font.dispose(); 
        }
        // Libérer les ressources de la police

    }

    
    

   