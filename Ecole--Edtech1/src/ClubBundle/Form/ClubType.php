<?php

namespace ClubBundle\Form;

use blackknight467\StarRatingBundle\Form\RatingType;
use Doctrine\ORM\EntityRepository;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class ClubType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('nomclub')
            ->add('User', EntityType::class, [
                'class' => 'AppBundle:User',
                'choice_label' => 'nom',
                'query_builder' =>
                    function (EntityRepository $repo) {
                        $xx = "a:1:{i:0;s:15:\"ROLE_ENSEIGNANT\";}";
                        return $repo->createQueryBuilder('c')->andWhere('c.roles = :xx')->setParameter('xx', $xx);
                    },
                'multiple' => false,
                'expanded' => false
            ])
            ->add('file');
        /*
          ->add('User', EntityType::class, ['class' => 'AppBundle:User',
                        'choice_label' => 'nom', 'multiple' => false, 'expanded' => false]);

        'query_builder' => function () {
                            return function(EntityRepository $repo)  {
                                return $repo->createQueryBuilder('c')->select('c')->from('AppBundle:User','c')->andWhere('m.roles = \'a:1:{i:0;s:15:"ROLE_ENSEIGNANT";}');
                            };
                        },
         * */

    }

    /**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'ClubBundle\Entity\Club'
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getBlockPrefix()
    {
        return 'clubbundle_club';
    }


}
