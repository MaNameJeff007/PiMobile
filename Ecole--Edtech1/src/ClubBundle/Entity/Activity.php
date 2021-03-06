<?php

namespace ClubBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Activity
 *
 * @ORM\Table(name="activity")
 * @ORM\Entity(repositoryClass="ClubBundle\Repository\ActivityRepository")
 */
class Activity
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
     * @ORM\Column(name="nomActivite", type="string", length=255)
     */
    private $nomActivite;

    /**
     * @var string
     *
     * @ORM\Column(name="typeActivite", type="string", length=255)
     */
    private $typeActivite;


    /**
     * @var integer
     *
     * @ORM\Column(name="vote", type="integer")
     */
    private $vote=0;



    /**
     * @ORM\ManyToOne(targetEntity="AppBundle\Entity\User")
     * @ORM\JoinColumn(referencedColumnName="id")
     */

    private $User;
    /**
     * @ORM\ManyToMany(targetEntity="AppBundle\Entity\User")
     * @ORM\JoinTable(name="usersActivityPartcipant")
     */
    private $UsersPartcipant;
    /**
     * @ORM\ManyToMany(targetEntity="AppBundle\Entity\User")
     * @ORM\JoinTable(name="usersActivityVote")
     */
    private $UsersVote;
//    /**
//     * Constructor
//     */
//    public function __construct()
//    {
//        $this->Activ = new \Doctrine\Common\Collections\ArrayCollection();
//    }

    /**
     * Get id
     *
     * @return integer
     */
    public function getId()
    {
        return $this->id;
    }

    /**
     * Set nomActivite
     *
     * @param string $nomActivite
     *
     * @return Activity
     */
    public function setNomActivite($nomActivite)
    {
        $this->nomActivite = $nomActivite;

        return $this;
    }

    /**
     * Get nomActivite
     *
     * @return string
     */
    public function getNomActivite()
    {
        return $this->nomActivite;
    }

    /**
     * Set typeActivite
     *
     * @param string $typeActivite
     *
     * @return Activity
     */
    public function setTypeActivite($typeActivite)
    {
        $this->typeActivite = $typeActivite;

        return $this;
    }

    /**
     * Get typeActivite
     *
     * @return string
     */
    public function getTypeActivite()
    {
        return $this->typeActivite;
    }



//    /**
//     * Get activ
//     *
//     * @return \Doctrine\Common\Collections\Collection
//     */
//    public function getActiv()
//    {
//        return $this->Activ;
//    }

    /**
     * Set user
     *
     * @param \AppBundle\Entity\User $user
     *
     * @return Activity
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

    /**
     * Add usersPartcipant
     *
     * @param \AppBundle\Entity\User $usersPartcipant
     *
     * @return Activity
     */
    public function addUsersPartcipant(\AppBundle\Entity\User $usersPartcipant)
    {
        $this->UsersPartcipant[] = $usersPartcipant;

        return $this;
    }

    /**
     * Remove usersPartcipant
     *
     * @param \AppBundle\Entity\User $usersPartcipant
     */
    public function removeUsersPartcipant(\AppBundle\Entity\User $usersPartcipant)
    {
        $this->UsersPartcipant->removeElement($usersPartcipant);
    }

    /**
     * Get usersPartcipant
     *
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getUsersPartcipant()
    {
        return $this->UsersPartcipant;
    }

    /**
     * Set vote
     *
     * @param integer $vote
     *
     * @return Activity
     */
    public function setVote($vote)
    {
        $this->vote = $vote;

        return $this;
    }

    /**
     * Get vote
     *
     * @return integer
     */
    public function getVote()
    {
        return $this->vote;
    }

    /**
     * Add usersVote
     *
     * @param \AppBundle\Entity\User $usersVote
     *
     * @return Activity
     */
    public function addUsersVote(\AppBundle\Entity\User $usersVote)
    {
        $this->UsersVote[] = $usersVote;

        return $this;
    }

    /**
     * Remove usersVote
     *
     * @param \AppBundle\Entity\User $usersVote
     */
    public function removeUsersVote(\AppBundle\Entity\User $usersVote)
    {
        $this->UsersVote->removeElement($usersVote);
    }

    /**
     * Get usersVote
     *
     * @return \Doctrine\Common\Collections\Collection
     */
    public function getUsersVote()
    {
        return $this->UsersVote;
    }
    /**
     * Constructor
     */
    public function __construct()
    {
        $this->UsersPartcipant = new \Doctrine\Common\Collections\ArrayCollection();
        $this->UsersVote = new \Doctrine\Common\Collections\ArrayCollection();
    }

}
