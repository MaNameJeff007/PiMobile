<?php

namespace ClubBundle\Form;

use Doctrine\ORM\EntityRepository;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class ActivityType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder->add('nomActivite')
            ->add('typeActivite')
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
        ]);
    }

    /**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'ClubBundle\Entity\Activity'
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getBlockPrefix()
    {
        return 'clubbundle_activity';
    }


}
