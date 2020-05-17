<?php

namespace ClubBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use SBC\NotificationsBundle\Builder\NotificationBuilder;
use SBC\NotificationsBundle\Model\NotifiableInterface;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * Evenement
 *
 * @ORM\Table(name="evenement")
 * @ORM\Entity(repositoryClass="ClubBundle\Repository\EvenementRepository")
 */
class Evenement implements NotifiableInterface, \JsonSerializable
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
     * @ORM\ManyToOne(targetEntity="ClubBundle\Entity\Club")
     * @ORM\JoinColumn(referencedColumnName="id" ,onDelete="CASCADE")
     */

    private $Club;

    /**
     * @ORM\ManyToMany(targetEntity="AppBundle\Entity\User")
     * @ORM\JoinTable(name="participantEvent")
     */
        private $Event;

    /**
     * @var string
     *
     * @ORM\Column(name="nom_evenement", type="string", length=255)
     *
     * @Assert\NotBlank()
     *
     */
    private $nomEvenement;
    /**
     * @ORM\Column(type="string",length=255,nullable=true)
     */
    public $nomImage;
    /**
     * @Assert\File(maxSize="5000k")
     */
    public $file;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="heure_debut", type="datetime")
     *
     */
    private $heureDebut;

    /**
     * @var \DateTime
     *
     * @ORM\Column(name="heure_fin", type="datetime")
     *
     * @Assert\GreaterThan(propertyPath="heureDebut")
     *
     */
    private $heureFin;



    /**
     * Get id
     *
     * @return int
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * Set nomEvenement
     *
     * @param string $nomEvenement
     *
     * @return Evenement
     */
    public function setNomEvenement($nomEvenement)
    {
        $this->nomEvenement = $nomEvenement;

        return $this;
    }

    /**
     * Get nomEvenement
     * @return string
     */
    public function getNomEvenement()
    {
        return $this->nomEvenement;
    }


    /**
     * Set heureDebut
     *
     * @param \DateTime $heureDebut
     *
     * @return Evenement
     */
    public function setHeureDebut($heureDebut)
    {
        $this->heureDebut = $heureDebut;

        return $this;
    }

    /**
     * Get heureDebut
     *
     * @return \DateTime
     */
    public function getHeureDebut()
    {
        return $this->heureDebut;
    }

    /**
     * Set heureFin
     *
     * @param \DateTime $heureFin
     *
     * @return Evenement
     */
    public function setHeureFin($heureFin)
    {
        $this->heureFin = $heureFin;

        return $this;
    }

    /**
     * Get heureFin
     *
     * @return \DateTime
     */
    public function getHeureFin()
    {
        return $this->heureFin;
    }

    /**
     * Set club
     *
     * @param \ClubBundle\Entity\Club $club
     *
     * @return Evenement
     */
    public function setClub(\ClubBundle\Entity\Club $club = null)
    {
        $this->Club = $club;

        return $this;
    }

    /**
     * Get club
     *
     * @return \ClubBundle\Entity\Club
     */
    public function getClub()
    {
        return $this->Club;
    }

    public function getWebPath()
    {
        return null === $this->nomImage ? null : $this->getUploadDir() . '/' . $this->nomImage;
    }

    protected function getUploadRootDir()
    {
        return __DIR__ . '/../../../web/' . $this->getUploadDir();
    }

    protected function getUploadDir()
    {
        return 'images';
    }

    public function uploadProfilePicture()
    {
        $this->file->move($this->getUploadRootDir(), $this->file->getClientOriginalName());
        $this->nomImage = $this->file->getClientOriginalName();
        $this->file = null;
    }

    /**
     * Set nomImage
     *
     * @param string $nomImage
     *
     * @return Club
     */
    public function setNomImage($nomImage)
    {
        $this . $this->nomImage == $nomImage;
        return $this;
    }

    /**
     * Get NomImage
     *
     * @return string
     */
    public function getNomImage()
    {
        return $this->nomImage;
    }



    /**
     * Constructor
     */
    public function __construct()
    {
        $this->Event = new \Doctrine\Common\Collections\ArrayCollection();
    }

    /**
     * Add event
     *
     * @param \AppBundle\Entity\User $event
     *
     * @return Evenement
     */
    public function addEvent(\AppBundle\Entity\User $event)
    {
        $this->Event[] = $event;

        return $this;
    }

    /**
     * Remove event
     *
     * @param \AppBundle\Entity\User $event
     */
    public function removeEvent(\AppBundle\Entity\User $event)
    {
        $this->Event->removeElement($event);
    }

    /**
     * Get event
     *
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getEvent()
    {
        return $this->Event;
    }

    public function notificationsOnCreate(NotificationBuilder $builder)
    {
        // TODO: Implement notificationsOnCreate() method.
        $notification = new Notification();
        $notification
            ->setTitle('Nouvelle Evenement')
            ->setDescription(' A été crée')
            ->setRoute('#')
            // ->setParameters(array('id' => $this->userToClaim))
        ;
        //$notification->setIcon($this->getUserr()->getUsername());

        $builder->addNotification($notification);

        return $builder;
    }

    public function notificationsOnUpdate(NotificationBuilder $builder)
    {
        return $builder;
    }

    public function notificationsOnDelete(NotificationBuilder $builder)
    {
        return $builder;
    }

    function jsonSerialize()
    {
        return get_object_vars($this);
    }
}
