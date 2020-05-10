<?php

    namespace ScolariteBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use Doctrine\Common\Collections\ArrayCollection;
use Symfony\Component\Validator\Constraints as Assert;
/**
 * Matiere
 *
 * @ORM\Table(name="matiere")
 * @ORM\Entity(repositoryClass="ScolariteBundle\Repository\MatiereRepository")
 */
class Matiere
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
     * @ORM\Column(name="nom", type="string", length=255, unique=true)
     */
    private $nom;

    /**
     * @var int
     *
     * @ORM\Column(name="nbH", type="integer")
     * @Assert\NotBlank()
     * @Assert\Length(min=0,max=42,minMessage="erreur" , maxMessage="vous avez depassé le nbre d'heures maximal")
     */
    private $nbH;

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
     * Set nom
     *
     * @param string $nom
     *
     * @return Matiere
     */
    public function setNom($nom)
    {
        $this->nom = $nom;

        return $this;
    }

    /**
     * Get nom
     *
     * @return string
     */
    public function getNom()
    {
        return $this->nom;
    }

    /**
     * Set nbH
     *
     * @param integer $nbH
     *
     * @return Matiere
     */
    public function setNbH($nbH)
    {
        $this->nbH = $nbH;

        return $this;
    }

    /**
     * Get nbH
     *
     * @return int
     */
    public function getNbH()
    {
        return $this->nbH;
    }


    /**
     * @ORM\OneToMany(targetEntity="EnseignantBundle\Entity\Moyennes", mappedBy="matiere")
     */
    private $moyennes;

    /**
     * @return ArrayCollection
     */
    public function getMoyennes()
    {
        return $this->moyennes;
    }

    /**
     * @param ArrayCollection $moyennes
     */
    public function setMoyennes($moyennes)
    {
        $this->moyennes = $moyennes;
    }

    /**
     * @ORM\OneToMany(targetEntity="EnseignantBundle\Entity\Notes", mappedBy="matiere")
     */
    private $notes;


    /**
     * @return ArrayCollection
     */
    public function getNotes()
    {
        return $this->notes;
    }

    /**
     * @param ArrayCollection $notes
     */
    public function setNotes($notes)
    {
        $this->notes = $notes;
    }

    public function __construct()
    {
        $this->notes = new ArrayCollection();
        $this->moyennes= new ArrayCollection();
    }

    public  function __toString()
    {
        return $this->getNom();
    }
}

