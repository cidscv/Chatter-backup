USE [ChatterProject]
GO
/****** Object:  StoredProcedure [dbo].[CreateMessage]    Script Date: 3/26/2020 12:55:31 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
ALTER PROCEDURE [dbo].[CreateMessage]
	-- Add the parameters for the stored procedure here
	@message NVARCHAR(1000) = NULL,
	@userid int,
	@channelid int,
	@messageid int OUTPUT
AS
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

    -- Insert statements for procedure here
	INSERT INTO [message]
	(userid, message, channelid)
	VALUES
	(@userid, @message, @channelid);

	SET @messageid = SCOPE_IDENTITY();
END
