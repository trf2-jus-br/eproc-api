select Concat(cod_trf_judicial, cod_localidade_judicial) id, 
       des_localidade_judicial                           nome 
from   localidade_judicial 
order  by des_localidade_judicial; 