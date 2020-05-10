<?php

namespace EnseignantBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Moyennes
 *
 * @ORM\Table(name="moyennes")
 * @ORM\Entity(repositoryClass="EnseignantBundle\Repository\MoyennesRepository")
 */
class Moyennes
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
     * @ORM\Column(name="trimestre", type="integer")
     */
    private $trimestre;

    /**
     * @ORM\ManyToOne(targetEntity="AppBundle\Entity\User", inversedBy="moyennes")
     * @ORM\JoinColumn(name="eleve_id",referencedColumnName="id")
     */
    private $eleve;

    /**
     * @ORM\ManyToOne(targetEntity="ScolariteBundle\Entity\Matiere", inversedBy="moyennes")
     * @ORM\JoinColumn(name="matiere",referencedColumnName="id")
     */
    private $matiere;

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

    /**
     * @return mixed
     */
    public function getEleve()
    {
        return $this->eleve;
    }

    /**
     * @param mixed $eleve
     */
    public function setEleve($eleve)
    {
        $this->eleve = $eleve;
    }

    /**
     * @return int
     */
    public function getTrimestre()
    {
        return $this->trimestre;
    }

    /**
     * @param int $trimestre
     */
    public function setTrimestre($trimestre)
    {
        $this->trimestre = $trimestre;
    }

    /**
     * @var float
     *
     * @ORM\Column(name="moyenne", type="float")
     */
    private $moyenne;


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
     * Set moyenne
     *
     * @param float $moyenne
     *
     * @return Moyennes
     */
    public function setMoyenne($moyenne)
    {
        $this->moyenne = $moyenne;

        return $this;
    }

    /**
     * Get moyenne
     *
     * @return float
     */
    public function getMoyenne()
    {
        return $this->moyenne;
    }
}

