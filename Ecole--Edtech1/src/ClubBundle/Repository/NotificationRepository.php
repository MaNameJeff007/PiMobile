<?php

namespace ClubBundle\Repository;

class NotificationRepository extends \Doctrine\ORM\EntityRepository
{
    public function findnotseen()
    {
        return $this->getEntityManager()
            ->createQuery("select m from ClubBundle:Notification m where m.seen = 0    ")
            ->getResult();
    }

    public function searchAll()
    {
        return $this->getEntityManager()
            ->createQuery("select m from ClubBundle:Notification m   ORDER BY  m.id DESC ")
            ->getResult();
    }
}