<?php

namespace ScolariteBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Coeff
 *
 * @ORM\Table(name="coeff")
 * @ORM\Entity(repositoryClass="ScolariteBundle\Repository\CoeffRepository")
 *
 */
class Coeff
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
     * @ORM\Column(name="valeur", type="integer")
     */
    private $valeur;

    /**
     * @return int
     */
    public function getValeur()
    {
        return $this->valeur;
    }

    /**
     * @param int $valeur
     */
    public function setValeur($valeur)
    {
        $this->valeur = $valeur;
    }

    /**
     * @var
     * @ORM\ManyToOne(targetEntity="Classe")
     * ORM\JoinColumn(name="niveau",referencedColumnName=Id)
     */
    private $niveau;

    /**
     * @var
     * @ORM\ManyToOne(targetEntity="Matiere")
     * ORM\JoinColumn(name="matiere",referencedColumnName=Id)
     */
    private $matiere;


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
     * @param mixed $niveau
     * @return Coeff
     */
    public function setNiveau($niveau)
    {
        $this->niveau = $niveau;

        return $this;
    }

    /**
     * @return mixed
     */
    public function getNiveau()
    {
        return $this->niveau;
    }

    /**
     * @param mixed $matiere
     * @return Coeff
     */
    public function setMatiere($matiere)
    {
        $this->matiere = $matiere;

        return $this;
    }

    /**
     * @return mixed
     */
    public function getMatiere()
    {
        return $this->matiere;
    }


}

