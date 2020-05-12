<?php

namespace ScolariteBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Bridge\Doctrine\Validator\Constraints\UniqueEntity;
use Doctrine\Common\Collections\ArrayCollection;
/**
 * Classe
 *
 * @ORM\Table(name="classe")
 * @ORM\Entity(repositoryClass="ScolariteBundle\Repository\ClasseRepository")
 */
class Classe
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
     * @ORM\Column(name="niveau", type="integer")
     */
    private $niveau;

    /**
     * @var string
     *
     * @ORM\Column(name="libelle", type="string", length=255, unique=true)
     * @Assert\NotBlank()
     * @Assert\Length(min="3", minMessage="libelle non valide")
     */
    private $libelle;

    /**
     * @return string
     */
    public function getLibelle()
    {
        return $this->libelle;
    }

    /**
     * @param string $libelle
     */
    public function setLibelle($libelle)
    {
        $this->libelle = $libelle;
    }
    /**
     * @var int
     *
     * @ORM\Column(name="capacite", type="integer")
     * @Assert\NotBlank()
     */
    private $capacite;

    /**
     * @ORM\OneToMany(targetEntity="AppBundle\Entity\User", mappedBy="classedeseleves")
     */
    private $eleves;

    /**
     * @ORM\OneToMany(targetEntity="AppBundle\Entity\User", mappedBy="classe")
     */
    private $enseignants;

    /**
     * @return ArrayCollection
     */
    public function getEleves()
    {
        return $this->eleves;
    }

    /**
     * @param ArrayCollection $eleves
     */
    public function setEleves($eleves)
    {
        $this->eleves = $eleves;
    }

    /**
     * @return ArrayCollection
     */
    public function getEnseignants()
    {
        return $this->enseignants;
    }

    /**
     * @param ArrayCollection $enseignants
     */
    public function setEnseignants($enseignants)
    {
        $this->enseignants = $enseignants;
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

    /**
     * Set niveau
     *
     * @param integer $niveau
     *
     * @return Classe
     */
    public function setNiveau($niveau)
    {
        $this->niveau = $niveau;

        return $this;
    }

    /**
     * Get niveau
     *
     * @return int
     */
    public function getNiveau()
    {
        return $this->niveau;
    }

    /**
     * Set capacite
     *
     * @param integer $capacite
     *
     * @return Classe
     */
    public function setCapacite($capacite)
    {
        $this->capacite = $capacite;

        return $this;
    }

    /**
     * Get capacite
     *
     * @return int
     */
    public function getCapacite()
    {
        return $this->capacite;
    }

    public function __toString()
    {
        return (string)$this->id;
    }

    public function __construct()
    {
        //parent::__construct();
        $this->eleves = new ArrayCollection();
        $this->enseignants = new ArrayCollection();
    }
}

