- Si vous n'avez ni Java et/ou ni Wamp (ou Lamp) vous devez avoir une connexion internet afin de télécharger les différents installateurs.

- Si ce n'est pas déjà fait téléchargez Java à cette adresse:
https://java.com/fr/

- Installez le.

- Si ce n'est pas déjà fait téléchargez
Wamp (pour Windows) à cette adresse:
https://sourceforge.net/projects/wampserver/files/
(pour Wamp vour aurez besoin (si non installé) de
Redistribuable Visual C++ pour Visual Studio  2015:
https://www.microsoft.com/fr-FR/download/details.aspx?id=48145 
et Redistribuable Visual C++ pour Visual Studio  2013:
https://www.microsoft.com/fr-FR/download/details.aspx?id=40784 
Si vous êtes sur Windows 10 vous devrez désactiver IIS pour executer correctement Wamp:
https://openclassrooms.com/forum/sujet/probleme-wampserver-windows-10#message-89111670 )
Installez le (lancer l'installateur avec les droits d'administrateurs).

ou Lamp (pour Linux):
https://doc.ubuntu-fr.org/lamp
https://doc.ubuntu-fr.org/phpmyadmin

On a travaillé sur Wamp (Windows) et Eclipse:


- Executer Wampserver (wampmanager.exe)

- Si ce n'est pas déjà fait lancez votre navigateur de recherche.Puis tapez "localhost" dans la barre de recherche de site pour vérifiez que c'est bien installé 
et n'oubliez pas de vérifier aussi que l'icone de WAMP à la couleur VERTE.

- Dans la partie "Vos alias" cliquez sur "phpmyadmin"

- Mettez "root" dans le champ "Utilisateur" et laissez le champ "Mot de passe" vide. Le champ “Choix du serveur” doit être sur “MySQL”. Connectez-vous.

- Cliquez sur "Bases de données" dans la barre du haut.

- Dans la partie "Créer une base de données" entrez le nom de la base de données (par défaut "projetbd") et cliquez sur "créer".

-Si toute fois vous souhaitez utiliser un autre nom de base de données assurez-vous d'avoir modifié le nom initial dans le fichier de configuration du serveur, et le MAIN du serveur.

- Importer la base de données ou ouvrez la dans un éditeur et copiez son contenu puis coller dans la partier "SQL" de mysql et puis exécutez.

-Importez les deux projets (CLIENT ET SERVEUR) dans eclipse

-Liez le projet CLIENT à celui du SERVEUR en faisant : clic droit sur le projet-> proprieties->Java Build Path -> Projet-> Add -> Apply -> Apply and close  

-Ajoutez le JDBC CONNECTOR dans les deux projets en faisant : clic droit sur le projet-> proprieties->Java Build Path -> Librairies> Add -> Apply -> Apply and close  

- Assurez vous de bien vérifier les fichiers configurations du serveur et du client.

- Vérifiez le port sur lequel mysql écoute. Chez nous c'est le port 3303. Ce port représent le port pour la base de données. N'oublie pas de le modifer dans dans la classe de connexion aussi

- Cherchez un port non utilisé par les processus de votre ordinateur. Chez nous c'est le port 3302. Ce port doit-etre le meme dans les deux fichiers de configurations et dans les deux classes principales (MAIN) des deux projets (Client et Serveur).

-Configurer l'adresse IP du serveur en faisant: clic droit sur l'icone du reseau en bas à droit de votre PC près de l'icone de la 
batterie -> ouvrir les paramètres et réseaux ethernet -> clic droit sur ethernet -> proprietes -> IPV4 -> Proprietes -> Utiliser l'adresse IP suivante -> Entrez
une adresse IP locale 192.168.1.128 par exempe ->tabulation -> passerelle (192.168.1.1)-> IP serveur (192.168.1.18) -> Ok -> fermer

- Lancez le MAIN du SERVEUR en première position car c'est lui qui est lié à la base de données. 

-Si vous désirez vous connecter en tant que CLIENT il vous faudra lancer le MAIN du CLient sans FERMER le SERVEUR

-Verifier dans la base de donnees un utilisateur existant ou creer un nouveau à partir de l'interface du serveur 

-Puis connectez vous 

-Si vous souhaitez lancer l'executable du projet sans passer par eclipse c'est possible mais pour le client il faudra ouvrir eclipse et le lancer 
car le client ne peut pas communiquer avec la base de données sans le serveur. On a malheursement pas trouvé un moyen pour pouvoir faire les deux exécutables en mm temps car on 
a pas fait un bouton sur l'interface serveur nous permettant d'acceder au client.

-C'est tout pour l'utilisation de ce projet

