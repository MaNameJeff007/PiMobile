<?php

namespace GestionBundle\Form;

use Doctrine\ORM\EntityRepository;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class PermutationType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        //$eleve = $options['eleve_id'];
        $builder
            /*->add('niveau', ChoiceType::class,array(
                'required' => true,
                'choices'=>array(
                    'Choisissez le niveau'=>'',
                    '1ère Année'=>'1ere Annee',
                    '2ème Année'=>'2eme Annee',
                    '3ème Année'=>'3eme Annee',
                    '4ème Année'=>'4eme Annee',
                    '5ème Année'=>'5eme Annee',
                    '6ème Année'=>'6eme Annee'))*/
           /* ->add('eleve', EntityType::class, [
                'class' => 'AppBundle:User', 'query_builder' => function (EntityRepository $er) use ($eleve) {
                    return $er->createQueryBuilder('u')
                        ->where('u.roles = :role')->andWhere('u.parent_id= :')
                        ->setParameter('role', 'a:1:{i:0;s:10:"ROLE_ELEVE";}')->setParameter('eleve_id', $eleve);
                }])*/
            ->add('classeS')->add('raison');
    }/**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'GestionBundle\Entity\Permutation'
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getBlockPrefix()
    {
        return 'gestionbundle_permutation';
    }


}
