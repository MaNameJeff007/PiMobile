<?php

namespace EnseignantBundle\Entity;

use Doctrine\ORM\Mapping as ORM;

/**
 * Bulletin
 *
 * @ORM\Table(name="bulletin")
 * @ORM\Entity(repositoryClass="EnseignantBundle\Repository\BulletinRepository")
 */
class Bulletin
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
      @ORM\Column(name="eleve", type="integer")
     */
    private $eleve;

    /**
     * @var int
    @ORM\Column(name="trimestre", type="integer")
     */
    private $trimestre;

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
     * @return int
     */
    public function getEleve()
    {
        return $this->eleve;
    }

    /**
     * @param int $eleve
     */
    public function setEleve($eleve)
    {
        $this->eleve = $eleve;
    }


    /**
     * @var string
      @ORM\Column(name="classe", type="string", length=255)
     */
    private $classe;

    /**
     * @return string
     */
    public function getClasse()
    {
        return $this->classe;
    }

    /**
     * @param string $classe
     */
    public function setClasse($classe)
    {
        $this->classe = $classe;
    }


    /**
     * @var float
     *
     * @ORM\Column(name="moyenne", type="float", nullable=true)
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
     * @return Bulletin
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

