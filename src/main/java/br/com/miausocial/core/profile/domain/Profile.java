package br.com.miausocial.core.perfil.domain;

import br.com.miausocial.core.user.domain.AppUser;
import br.com.miausocial.infra.ddd.AbstractEntity;
import br.com.miausocial.shared.Image;

import java.util.UUID;

public class Perfil extends AbstractEntity<UUID> {
    private AppUser appUser;
    private Image fotoPerfil;
    private Image banner;
    
}
