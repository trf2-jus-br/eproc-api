select classej.id_classe_judicial id, 
       classej.des_classe         nome, 
       classej.cod_classe         classecnj 
from   classe_competencia_localidade ccl 
       inner join localidade_judicial lj 
               on ccl.id_localidade_judicial = lj.id_localidade_judicial 
       inner join competencia_judicial cj 
               on ccl.cod_competencia = cj.cod_competencia 
       inner join classe_judicial classej 
               on ccl.cod_classe = classej.cod_classe 
where  ccl.sin_ativo = 'S' 
       and lj.cod_trf_judicial = ? 
       and lj.cod_localidade_judicial = ? 
       and ccl.cod_competencia = ? 
order  by classej.des_classe; 