package com.esgi.donjons.aventurier.service;

import com.esgi.donjons.aventurier.exception.AventurierAlreadyExistsException;

class AventurierServiceTest {

    private AventurierService aventurierService;

    @BeforeEach
    void setUp() {
        aventurierService = new AventurierService(new FakeAventurierDao());
    }

    @Test
    void devraitAjouterUnAventurier() {
        Aventurier aventurier = new Aventurier(1L, "Thrall", ClasseHeros.MAGE, 12);

        Aventurier resultat = aventurierService.creer(aventurier);

        assertEquals("Thrall", resultat.nom());
    }

    @Test
    void devraitLeverUneExceptionSiAventurierExisteDeja() {
        Aventurier aventurier = new Aventurier(1L, "Thrall", ClasseHeros.MAGE, 12);

        aventurierService.creer(aventurier);

        assertThrows(AventurierAlreadyExistsException.class, () ->
                aventurierService.creer(aventurier)
        );
    }

    @Test
    void devraitTrouverUnAventurierParId() {
        Aventurier aventurier = new Aventurier(1L, "Illidan", ClasseHeros.VOLEUR, 20);
        aventurierService.creer(aventurier);

        Aventurier resultat = aventurierService.trouverParId(1L);

        assertEquals("Illidan", resultat.nom());
    }

    @Test
    void devraitLeverUneExceptionSiAventurierIntrouvable() {
        assertThrows(AventurierNotFoundException.class, () ->
                aventurierService.trouverParId(999L)
        );
    }

    static class FakeAventurierDao implements AventurierDao {

        private final Map<Long, Aventurier> stockage = new HashMap<>();

        @Override
        public Aventurier save(Aventurier aventurier) {
            stockage.put(aventurier.id(), aventurier);
            return aventurier;
        }

        @Override
        public Optional<Aventurier> findById(Long id) {
            return Optional.ofNullable(stockage.get(id));
        }

        @Override
        public Optional<Aventurier> findByNom(String nom) {
            return stockage.values()
                    .stream()
                    .filter(a -> a.nom().equalsIgnoreCase(nom))
                    .findFirst();
        }
    }
}