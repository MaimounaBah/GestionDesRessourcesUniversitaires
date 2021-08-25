# GestionDesRessourcesUniversitaires
+ Si vous n'avez ni **Java** et/ou ni **Wamp** (ou **Lamp**) vous devez avoir une connexion internet afin de télécharger les différents installateurs.

+ Si ce n'est pas déjà fait téléchargez **Java** à cette adresse:
[Lien](https://java.com/fr/)

+ Installez le.

1. Si ce n'est pas déjà fait téléchargez **Wamp** (pour *Windows*) à cette adresse:
[lien](https://sourceforge.net/projects/wampserver/files/)

**NB** : (Pour **Wamp** vous aurez besoin (si non installé) de : 
+ Redistribuable Visual C++ pour Visual Studio 2015: [lien](https://www.microsoft.com/fr-FR/download/details.aspx?id=48145)

+ et Redistribuable Visual C++ pour Visual Studio 2013: [lien](https://www.microsoft.com/fr-FR/download/details.aspx?id=40784)

+ Si vous êtes sur Windows 10 vous allez devoir désactiver ***IIS*** pour executer correctement Wamp (expliqué sur ce lien): [lien](https://openclassrooms.com/forum/sujet/probleme-wampserver-windows-10#message-89111670)

+ Installez le (lancer l'installateur avec les droits d'administrateurs)

2. Si vous utilisez Linux, suivez ces deux liens:
[lien](https://doc.ubuntu-fr.org/lamp)
[lien](https://doc.ubuntu-fr.org/phpmyadmin)

---
##On a travaillé sur **Wamp** (*Windows*) et **Eclipse**

1. Executer Wampserver (wampmanager.exe)
+ Si ce n'est pas déjà fait lancez votre navigateur de recherche. Puis tapez *localhost* dans la barre de recherche du navigateur pour vérifier que c'est bien installé.
**NB** : N'oubliez pas de vérifier aussi que l'icône de WAMP à la couleur **VERTE** tout en bas à droite près de votre batterie.
2. Dans la partie *Vos alias* cliquez sur **phpmyadmin**
3. Mettez **root** dans le champ *Utilisateur* et laissez le champ **Mot de passe** vide. Le champ *Choix du serveur* doit être sur **MySQL**. Connectez-vous.
4. Cliquez sur **Bases de données** dans la barre de menu en haut

5. Dans la partie *Créer une base de données* , entrez le nom de la base de données (par défaut "projetbd") et cliquez sur **créer**.
6. Si toutes fois vous souhaitez utiliser *un autre nom de base de données*, assurez-vous d'avoir modifié le nom initial dans le fichier de **configuration et le MAIN du serveur**.

7. Importer la base de données ou ouvrez la dans un éditeur de texte comme **Sublime Text** et copiez son contenu puis coller dans la partie **SQL** de mysql et exécutez le script.

8. Importez les deux projets **ProjetS5_Client** et **ProjetS5_Serveur** dans *Eclipse*

9. Liez le projet *client* à celui du *serveur* en faisant : clic droit sur le projet-> proprieties->Java Build Path -> Projet-> Add -> Apply -> Apply and close  

10. Ajoutez le *JDBC CONNECTOR* dans les **deux projets** en faisant : clic droit sur projet-> proprieties->Java Build Path -> Librairies> Add -> Apply -> Apply and close  

11. Assurez vous de bien vérifier les **fichiers de configurations** du serveur et du client.

12. Vérifiez le port sur lequel **mysql** écoute. Chez nous, c'est le port **3303**. Ce port représent *le port pour la base de données*. N'oubliez pas de le modifer dans la classe de connexion aussi
13.  Cherchez un port non utilisé par les processus de votre ordinateur. Chez nous c'est le port 3302. **Ce port doit-etre le même dans les deux fichiers de configurations et dans les deux classes principales (MAIN) des deux projets (Client et Serveur)**.

14. Configurer l'adresse IP du serveur en faisant: clic droit sur l'icone du reseau en bas dans le coin droit de votre PC près de l'icone de la batterie -> ouvrir les paramètres et réseaux ethernet -> clic droit sur ethernet -> proprietes -> IPV4 -> Proprietes -> Utiliser l'adresse IP suivante -> Entrez
une adresse IP locale 192.168.1.128 par exempe ->tabulation -> passerelle (192.168.1.1)-> IP serveur (192.168.1.18) -> Ok -> fermer
15. Lancez le **MAIN dans le projet SERVEUR** en première position car c'est lui qui est lié à la base de données. 

16. Si vous désirez vous connecter en tant que **CLIENT**, il vous faudra lancer le **MAIN du CLient sans FERMER le SERVEUR déjà lancé à l'étape ***15*** **

17. Verifier dans la base de donnees un utilisateur existant ou créer un nouveau à partir de l'interface du serveur pour pouvoir faire des tests.?

18. Puis connectez vous 

19. Si vous souhaitez lancer l'executable du projet sans passer par Eclipse, c'est possible. Mais, pour le client il faudra ouvrir eclipse et le lancer car le client ne peut pas communiquer directement avec la base de données sans passer par le serveur. On a malheursement pas trouvé un moyen pour pouvoir faire les deux exécutables en même temps car on 
a pas fait un bouton sur l'interface serveur nous permettant d'acceder au client.

**C'est tout pour l'utilisation de ce projet**



 



