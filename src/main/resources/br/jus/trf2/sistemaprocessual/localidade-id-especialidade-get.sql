select cj.cod_competencia id, 
       cj.des_competencia nome 
from   classe_competencia_localidade ccl 
       inner join localidade_judicial lj 
               on ccl.id_localidade_judicial = lj.id_localidade_judicial 
       inner join competencia_judicial cj 
               on ccl.cod_competencia = cj.cod_competencia 
where  ccl.sin_ativo = 'S' 
       and lj.cod_trf_judicial = ? 
       and lj.cod_localidade_judicial = ? 
group  by cj.cod_competencia, 
          cj.des_competencia 
order  by cj.des_competencia; 