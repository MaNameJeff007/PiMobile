<?php

namespace ScolariteBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use SBC\NotificationsBundle\Model\BaseNotification;

/**
 * Notification
 *
 * @ORM\Table(name="notification")
 * @ORM\Entity(repositoryClass="ScolariteBundle\Repository\NotificationRepository")
 */
class Notification extends BaseNotification implements \JsonSerializable
{
    /**
     * @var int
     *
     * @ORM\Column(name="id", type="integer")
     * @ORM\Id
     * @ORM\GeneratedValue(strategy="AUTO")
     */
    private $id;

    /**
     * @var int
     *
     * @ORM\Column(name="ens", type="integer", nullable=true)

     */
    private $ens;

    /**
     * @return int
     */
    public function getEns()
    {
        return $this->ens;
    }

    /**
     * @param int $ens
     */
    public function setEns($ens)
    {
        $this->ens = $ens;
    }

    /**
     * @return bool
     */
    public function isSeen()
    {
        return $this->seen;
    }

    /**
     * @param bool $seen
     */
    public function setSeen($seen)
    {
        $this->seen = $seen;
    }


    /**
     * Get id
     *
     * @return int
     */
    public function getId()
    {
        return $this->id;
    }



    function jsonSerialize()
    {
        return get_object_vars($this);
    }

    public function __toString()
    {
        return $this->Notification ;
    }
}

