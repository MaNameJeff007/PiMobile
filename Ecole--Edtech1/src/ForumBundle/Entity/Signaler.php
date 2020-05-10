<?php

namespace ForumBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Signaler
 *
 * @ORM\Table(name="signaler")
 * @ORM\Entity(repositoryClass="ForumBundle\Repository\SignalerRepository")
 */
class Signaler
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

    /**
     * @var int
     *
     * @ORM\Column(name="nombre", type="integer")
     */
    private $nombre=1;

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
     * Set nombre
     *
     * @param integer $nombre
     *
     * @return Signaler
     */
    public function setNombre($nombre)
    {
        $this->nombre = $nombre;

        return $this;
    }

    /**
     * Get nombre
     *
     * @return int
     */
    public function getNombre()
    {
        return $this->nombre;
    }

    /**
     * Set type
     *
     * @param string $type
     *
     * @return Signaler
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
}

