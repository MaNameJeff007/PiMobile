<?php

namespace ScolariteBundle\Repository;

/**
 * NotificationRepository
 *
 * This class was generated by the Doctrine ORM. Add your own custom
 * repository methods below.
 */
class NotificationRepository extends \Doctrine\ORM\EntityRepository
{





    public function GetNotEn($idp)
    {$query = $this->getEntityManager()->createQuery(

            'SELECT count(c.id) as nb , c.title,c.description, c.id, c.date, c.ens
             FROM AppBundle:User u inner join ScolariteBundle:Notification c  with u.id = c.ens where
              c.ens = :idp '
        )
            ->setParameter('idp', $idp);
        return $products = $query->getResult();
    }

    public function CountNotEn($idp)
    {$query = $this->getEntityManager()->createQuery(

        'SELECT count(c.id) as nb ,c.id
             FROM AppBundle:User u inner join ScolariteBundle:Notification c  with u.id = c.ens where
              u.id= :idp '
    )
        ->setParameter('idp', $idp);
        return $products = $query->getResult();
    }

}
