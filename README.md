Pour lancer l'appli, il faut lancer le treasureMapApplication.java en tant que java application.

Les chemins des fichiers d'entrée et de sortie sont définis dans le fichier application.properties de l'application Spring:
- Path fichier input : propriété spring.map.cfg.run (par défaut: file:C:/tmp/map.cfg).
- Path fichier output : propriété spring.map.write.file (par défaut: C:/tmp/output.txt).

 Une fois l'appli lancée,pour exécuter le programme, il suffit de se connecter à http://localhost:8080. Chaque rafraîchissement de la page trigger l'exécution du programme.
