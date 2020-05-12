<?php

namespace ScolariteBundle\Repository;
use Doctrine\ORM\Query;
/**
 * ClasseRepository
 *
 * This class was generated by the Doctrine ORM. Add your own custom
 * repository methods below.
 */
class ClasseRepository extends \Doctrine\ORM\EntityRepository
{
    public function GetClasseEleve($idp)
    {


        $query = $this->getEntityManager()->createQuery(

            'SELECT u.id , u.nom , u.prenom , c.libelle, c.id as classe
             FROM AppBundle:User u inner join ScolariteBundle:Classe c  with u.classe = c.id where
             u.parent = :idp '
        )
            ->setParameter('idp', $idp);
        return $products = $query->getResult();
    }

    public function GetClasseEleve2($idp)
    {
        $query = $this->getEntityManager()->createQuery(

            'SELECT c
             FROM AppBundle:User u inner join ScolariteBundle:Classe c  with u.classedeseleves = c.id where
             u.id = :idp '
        )
            ->setParameter('idp', $idp);
        return $products = $query->getResult();
    }

    public function GetUserParClasseElv($idp)
    {  //
        $query = $this->getEntityManager()->createQuery(

            'SELECT u
             FROM AppBundle:User u inner join ScolariteBundle:Classe c  with u.classedeseleves = c.id where
            c.libelle = :idp '
        )
            ->setParameter('idp', $idp);
        return $products = $query->getResult();
    }


    public function findCasse($l)
    {
        $query = $this->getEntityManager()->createQuery(

            'SELECT p
             FROM ScolariteBundle:Classe p
              WHERE p.libelle = :l'
        )->setParameter('l', $l);

        return $products = $query->getResult();
    }
}
