<?php

namespace ScolariteBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use AppBundle\Entity\User;
use SBC\NotificationsBundle\Builder\NotificationBuilder;
use SBC\NotificationsBundle\Model\NotifiableInterface;

/**
 * Seance
 *
 * @ORM\Table(name="seance")
 * @ORM\Entity(repositoryClass="ScolariteBundle\Repository\SeanceRepository")
 */
class Seance implements NotifiableInterface
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
     * @var
     * @ORM\ManyToOne(targetEntity="AppBundle\Entity\User")
     * ORM\JoinColumn(name="ens",referencedColumnName=Id)
     */
    private $enseignant;

    /**
     * @var
     * @ORM\ManyToOne(targetEntity="Classe")
     * ORM\JoinColumn(name="classe",referencedColumnName=Id)
     */
    private $classe;

    public function notificationsOnCreate(NotificationBuilder $builder)
    {
        $notification = new Notification();
        $notification->setTitle('Une nouvelle seance à été ajouté à votre emploi')
            ->setDescription($this->getJour())
            ->setRoute('emploi_En')
            ->setParameters(array('id' =>$this->getEnseignant()->getId()))
        ->setEns($this->getEnseignant()->getId());
$builder->addNotification($notification);
return $builder;
        //$em->persist($notification);
        //$em->flush();

        //$pusher=$this->get('mrad.pusher.notificaitons');
        //$pusher->trigger($notification);
    }

    public function notificationsOnUpdate(NotificationBuilder $builder)
    {
        $notification = new Notification();
        $notification->setTitle(' votre emploi à été modifié')
            ->setDescription($this->getJour())
            ->setRoute('emploi_En')
            ->setParameters(array('id' =>$this->getEnseignant()->getId()))
            ->setEns($this->getEnseignant()->getId());
        $builder->addNotification($notification);
        return $builder;
    }

    public function notificationsOnDelete(NotificationBuilder $builder)
    {
        $notification = new Notification();
        $notification->setTitle('Une séance à été dupprimé de  
       votre emploi')
            ->setDescription($this->getJour())
            ->setRoute('emploi_En')
            ->setParameters(array('id' =>$this->getEnseignant()->getId()))
            ->setEns($this->getEnseignant()->getId());
        $builder->addNotification($notification);
        return $builder;

    }

    /**
     * @var string
     *
     * @ORM\Column(name="jour", type="string", length=255)
     */
    private $jour;

    /**
     * @var string
     *
     * @ORM\Column(name="hdeb", type="string", length=255)
     */

    private $hdeb;

    /**
     * @return string
     */
    public function getHdeb()
    {
        return $this->hdeb;
    }

    /**
     * @param string $hdeb
     */
    public function setHdeb($hdeb)
    {
        $this->hdeb = $hdeb;
    }

    /**
     * @return string
     */
    public function getHfin()
    {
        return $this->hfin;
    }

    /**
     * @param string $hfin
     */
    public function setHfin($hfin)
    {
        $this->hfin = $hfin;
    }

    /**
     * @var string
     *
     * @ORM\Column(name="hfin",type="string", length=255)
     */
    private $hfin;

    /**
     * @var
     * @ORM\ManyToOne(targetEntity="Salle")
     * ORM\JoinColumn(name="salle",referencedColumnName=Id)
     */
    private $salle;

    /**
     * @var
     * @ORM\ManyToOne(targetEntity="Matiere")
     * ORM\JoinColumn(name="matiere",referencedColumnName=Id)
     */
    private $matiere;

    /**
     * @return int
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * @param int $id
     */
    public function setId($id)
    {
        $this->id = $id;
    }

    /**
     * @return mixed
     */
    public function getEnseignant()
    {
        return $this->enseignant;
    }

    /**
     * @param mixed $enseignant
     */
    public function setEnseignant($enseignant)
    {
        $this->enseignant = $enseignant;
    }

    /**
     * @return mixed
     */
    public function getClasse()
    {
        return $this->classe;
    }

    /**
     * @param mixed $classe
     */
    public function setClasse($classe)
    {
        $this->classe = $classe;
    }

    /**
     * @return string
     */
    public function getJour()
    {
        return $this->jour;
    }

    /**
     * @param string $jour
     */
    public function setJour($jour)
    {
        $this->jour = $jour;
    }


    /**
     * @return mixed
     */
    public function getSalle()
    {
        return $this->salle;
    }

    /**
     * @param mixed $salle
     */
    public function setSalle($salle)
    {
        $this->salle = $salle;
    }

    /**
     * @return mixed
     */
    public function getMatiere()
    {
        return $this->matiere;
    }

    /**
     * @param mixed $matiere
     */
    public function setMatiere($matiere)
    {
        $this->matiere = $matiere;
    }







}

