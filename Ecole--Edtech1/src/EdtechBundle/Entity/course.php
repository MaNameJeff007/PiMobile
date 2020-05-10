<?php

namespace EdtechBundle\Entity;

use Doctrine\ORM\Mapping as ORM;
use AppBundle\Entity\User;
use Symfony\Component\Form\Extension\Core\Type\IntegerType ;
use Symfony\Component\Validator\Constraints as Assert;

/**
 * course
 *
 * @ORM\Table(name="course")
 * @ORM\Entity(repositoryClass="EdtechBundle\Repository\courseRepository")
 */
class course
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
     * @ORM\Column(name="nom", type="string", length=250)
     */
    private $nom;

    /**
     * @var string
     *
     * @ORM\Column(name="description", type="string", length=255)
     */
    private $description;

    /**
     * @var string
     *
     * @ORM\Column(name="contenu", type="string", length=255)
     */
    private $contenu;

    /**
     * @var int
     *
     * @ORM\Column(name="type_intelligence", type="integer")
     * @Assert\Choice({1,2,3})
     */
    private $typeIntelligence;
    /**
     * @ORM\Column(type="integer")
     * @ORM\ManyToOne(targetEntity="AppBundle\Entity\User")
     * @Assert\Type(type="integer")
     * @Assert\Choice({1,2,3,4,5,6})
     *
     */
    private $niveau;
    /**
     * @ORM\ManyToOne(targetEntity="AppBundle\Entity\User", inversedBy="courses")
     */
    private $user;

    /**
     * @return mixed
     */
    public function getNiveau()
    {
        return $this->niveau;
    }

    /**
     * @param mixed $niveau
     */
    public function setNiveau($niveau)
    {
        $this->niveau = $niveau;
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
     * Set nom
     *
     * @param string $nom
     *
     * @return course
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
     * Set description
     *
     * @param string $description
     *
     * @return course
     */
    public function setDescription($description)
    {
        $this->description = $description;

        return $this;
    }

    /**
     * Get description
     *
     * @return string
     */
    public function getDescription()
    {
        return $this->description;
    }

    /**
     * Set contenu
     *
     * @param string $contenu
     *
     * @return course
     */
    public function setContenu($contenu)
    {
        $this->contenu = $contenu;

        return $this;
    }

    /**
     * Get contenu
     *
     * @return string
     */
    public function getContenu()
    {
        return $this->contenu;
    }

    /**
     * Set typeIntelligence
     *
     * @param integer $typeIntelligence
     *
     * @return course
     */
    public function setTypeIntelligence($typeIntelligence)
    {
        $this->typeIntelligence = $typeIntelligence;

        return $this;
    }

    /**
     * Get typeIntelligence
     *
     * @return int
     */
    public function getTypeIntelligence()
    {
        return $this->typeIntelligence;
    }

    /**
     * Set user
     *
     * @param \AppBundle\Entity\User $user
     *
     * @return course
     */
    public function setUser(\AppBundle\Entity\User $user = null)
    {
        $this->user = $user;

        return $this;
    }

    /**
     * Get user
     *
     * @return \AppBundle\Entity\User
     */
    public function getUser()
    {
        return $this->user;
    }
    public function __toString() {
        return $this->nom;
    }
}
