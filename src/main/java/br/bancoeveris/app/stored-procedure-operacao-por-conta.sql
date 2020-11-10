CREATE PROCEDURE SP_OperacoesPorConta
    @idConta BIGINT

AS
BEGIN
    SELECT 
        id,
        tipo,
        valor,
        contaDestinoId,
        contaOrigemId
    FROM Operacao WHERE contaDestinoId = @idConta

    UNION
    SELECT 
        id,
        tipo,
        valor,
        contaDestinoId,
        contaOrigemId
    FROM Operacao WHERE contaOrigemId = @idConta

END