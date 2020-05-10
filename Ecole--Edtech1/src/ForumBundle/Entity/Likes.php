<?php

namespace ForumBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Likes
 *
 * @ORM\Table(name="likes")
 * @ORM\Entity(repositoryClass="ForumBundle\Repository\LikesRepository")
 */
class Likes
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
     * @ORM\Column(name="createur", type="integer")
     */
    private $createur;

    /**
     * @ORM\ManyToOne(targetEntity="ForumBundle\Entity\Sujet")
     * @ORM\JoinColumn(name="sujet",referencedColumnName="id")
     */
    private $sujet=null;

    /**
     * @ORM\ManyToOne(targetEntity="ForumBundle\Entity\Commentaire")
     * @ORM\JoinColumn(name="commentaire",referencedColumnName="id")
     */
    private $commentaire=null;

    /**
     * @var string
     *
     * @ORM\Column(name="type", type="string", length=255)
     */
    private $type;


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
     * Set type
     *
     * @param string $type
     *
     * @return Likes
     */
    public function setType($type)
    {
        $this->type = $type;

        return $this;
    }

    /**
     * Get type
     *
     * @return string
     */
    public function getType()
    {
        return $this->type;
    }

    /**
     * @return int
     */
    public function getCreateur()
    {
        return $this->createur;
    }

    /**
     * @param int $createur
     */
    public function setCreateur($createur)
    {
        $this->createur = $createur;
    }

    /**
     * @return null
     */
    public function getSujet()
    {
        return $this->sujet;
    }

    /**
     * @param null $sujet
     */
    public function setSujet($sujet)
    {
        $this->sujet = $sujet;
    }

    /**
     * @return null
     */
    public function getCommentaire()
    {
        return $this->commentaire;
    }

    /**
     * @param null $commentaire
     */
    public function setCommentaire($commentaire)
    {
        $this->commentaire = $commentaire;
    }
}

