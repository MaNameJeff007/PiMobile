<?php

namespace ClubBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use SBC\NotificationsBundle\Builder\NotificationBuilder;
use SBC\NotificationsBundle\Model\NotifiableInterface;

/**
 * Club
 *
 * @ORM\Table(name="club")
 * @ORM\Entity(repositoryClass="ClubBundle\Repository\ClubRepository")
 */
class Club implements NotifiableInterface, \JsonSerializable
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
     * @var string
     *
     * @ORM\Column(name="nomclub", type="string", length=255)
     *
     * @Assert\NotBlank()
     */
    private $nomclub;


    /**
     * @ORM\ManyToOne(targetEntity="AppBundle\Entity\User")
     * @ORM\JoinColumn(referencedColumnName="id")
     */

    private $User;

    /**
     * @ORM\Column(type="string",length=255,nullable=true)
     */
    public $nomImage;


    /**
     * @ORM\Column(type="integer", options={"default": 0})
     */
    public $voteClub;

    /**
     * @Assert\File(maxSize="5000k")
     */
    public $file;

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
     * Set nomclub
     *
     * @param string $nomclub
     * @return Club
     */
    public function setNomclub($nomclub)
    {
        $this->nomclub = $nomclub;

        return $this;
    }

    /**
     * Get nomclub
     *
     * @return string
     */
    public function getNomclub()
    {
        return $this->nomclub;
    }

    /**
     * Set user
     *
     * @param \AppBundle\Entity\User $user
     *
     * @return Club
     */
    public function setUser(\AppBundle\Entity\User $user = null)
    {
        $this->User = $user;

        return $this;
    }

    /**
     * Get user
     *
     * @return \AppBundle\Entity\User
     */
    public function getUser()
    {
        return $this->User;
    }
#get l path mta3 win bech ysob l image
    public function getWebPath()
    {
        return null === $this->nomImage ? null : $this->getUploadDir() . '/' . $this->nomImage;
    }
#get directory
    protected function getUploadRootDir()
    {
        return __DIR__ . '/../../../web/' . $this->getUploadDir();
    }

    protected function getUploadDir()
    {
        return 'images';
    }
#tuploadi l image
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

    public function notificationsOnCreate(NotificationBuilder $builder)
    {
        // TODO: Implement notificationsOnCreate() method.
        $notification = new Notification();
        $notification
            ->setTitle('Nouveau Club')
            ->setDescription(' A été crée le')
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

    /**
     * Set voteClub
     *
     * @param integer $voteClub
     *
     * @return Club
     */
    public function setVoteClub($voteClub)
    {
        $this->voteClub = $voteClub;

        return $this;
    }

    /**
     * Get voteClub
     *
     * @return integer
     */
    public function getVoteClub()
    {
        return $this->voteClub;
    }
}
