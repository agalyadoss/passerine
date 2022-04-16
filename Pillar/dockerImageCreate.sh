VERSION_MINOR=$(bash $PWD/genSeq.sh PLRM1)
VERSION_MAJOR=$(bash $PWD/genSeq.sh PLRM2)
sudo docker build --tag durai145/pillar-sync.$VERSION_MAJOR.$VERSION_MINOR .
sudo docker images
