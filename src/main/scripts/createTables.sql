
/*首信易账户同步表
 * 当用户完成实名验证后，触发首信易开户请求，不管成功与否，都会在此表增加记录，以记录请求信息。
 * 也就是说，没有实名验证过的用户，在user_main中有记录，但在这张表中不会有记录
 * 所有SYN001001操作的请求记录，在这张表中都会有*/
CREATE TABLE payease_syn001001(

	msgid INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键，无业务含义',
	
	transCode CHAR(9) NOT NULL COMMENT '始终就是SYN001001，只是为了和报文字段保持对应',
	
	reqTime DATETIME NOT NULL COMMENT '向首信易发送请求的时间，对应SYN001001请求中的REQ_TIME',
	
	operationCode SMALLINT UNSIGNED NOT NULL COMMENT '首信易业务操作码',
 
	user INT UNSIGNED NOT NULL COMMENT '用户id，对应SYN001001请求中的USER',
	
	idType CHAR(2) NULL COMMENT '证件类型，可为空，对应SYN001001请求中的ID_TYPE',
	
	id CHAR(18) NULL COMMENT '用户证件号，可为空，对应SYN001001请求中的ID，操作码为10001时必填。注意在此表中，这不是主键的意思，而是一个普通的业务字段',
	
	userName VARCHAR(16) NULL COMMENT '经过认证的（身份证或其他证件）用户姓名，可为空，以及SYN001001请求中的USER_NAME',
	
	mobile BIGINT UNSIGNED NULL DEFAULT 0 COMMENT '手机号，对应SYN001001-MOBILE',
	
	accName VARCHAR(16) NULL COMMENT '开户名，如果USER_NAME不填，该字段就为必填项，对应SYN001001-ACC_NAME',
	
	accProvince VARCHAR(16) NULL COMMENT '开户省，对应SYN001001-ACC_PROVINCE',
	
	accCity VARCHAR(16) NULL COMMENT '开户市，对应SYN001001-ACC_CITY',
	
	accBankCode SMALLINT UNSIGNED NULL COMMENT '开户行代码',
	
	accBranchName VARCHAR(128) NULL COMMENT '开户分行支行名称，对应SYN001001-ACC_BRANCH_NAME',
	
	accNum VARCHAR(19) NULL COMMENT '银行账号，可为空，对应SYN001001-ACC_NUM',
	
	accType CHAR(2) NULL COMMENT '账号类型，可为空，对应SYN001001请求中的ACC_TYPE。00：卡；01：存折。默认00',
	
	accProp CHAR(2) NULL COMMENT '账号类别，可为空，对应SYN001001请求中的ACC_PROP。PR-对私，PU-对公，默认PR。操作码为：10004时必填',
	
	returnCode CHAR(4) NULL COMMENT '首信易返回码，对应SYN001001响应报文中的RETURN_CODE',
	
	returnMsg VARCHAR(64) NULL COMMENT '首信易返回结果描述，对应SYN001001响应报文中的RETURN_MSG',
  	
	returnTime DATETIME NULL COMMENT '首信易返回时间',
	
	INDEX idx_payease_syn001001_user (user),

	INDEX idx_payease_syn001001_userName (userName),

	INDEX idx_payease_syn001001_id (id),

	INDEX idx_payease_syn001001_accNum (accNum),
	
	INDEX idx_payease_syn001001_operationCode (operationCode),

	INDEX idx_payease_syn001001_returnCode (returnCode),
	
	INDEX idx_payease_syn001001_reqTime (reqTime)
  	
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*首信易  投标冻结/解冻 同步表
 * 用户投标成功后，向首信易发送TRS001002请求。*/
CREATE TABLE payease_trs001002(

	msgid INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键', 
	
	reqTime DATETIME NOT NULL COMMENT '向首信易发送请求的时间',
 
	operationCode SMALLINT UNSIGNED NOT NULL COMMENT '首信易业务操作码',
	
	serlNum INT UNSIGNED NOT NULL COMMENT '系统交易流水唯一标示，同loan_investor表id字段一致，对应TRS001002请求中的SERL_NUM',
	
	authId INT UNSIGNED NOT NULL COMMENT '冻结号码唯一不允许重复，同loan_investor表id字段一致，对应TRS001002请求中的AUTH_ID',
	    
    borrower INT UNSIGNED NOT NULL COMMENT '借款人的userId，，对应TRS001002请求中的BORROWER',
    
    borrowerId CHAR(18) NULL COMMENT '借款人的证件号，可为空，同user_main表idNo字段一致，对应TRS001002请求中的BORROWER_ID',
	
	borrowerName VARCHAR(16) NULL COMMENT '借款人的姓名，可为空，同user_main表realname字段一致，对应TRS001002请求中的BORROWER_NAME',

	contractNum INT UNSIGNED NOT NULL COMMENT '借款标的号（合同号）。这里用所投标的loanId代替，同loan_investor表loanId字段一致，对应TRS001002请求中的CONTRACT_NUM',
    
    certNum INT UNSIGNED NOT NULL COMMENT '出借借据编号（凭证）。对应TRS001002请求中的CERT_NUM',
  
    lender INT UNSIGNED NOT NULL COMMENT '投资人的userId，同loan_investor表investorUserId字段一致，对应TRS001002请求中的LENDER',
    
    lenderId CHAR(18) NULL COMMENT '投资人的证件号，可为空，同user_main表idNo字段一致，对应TRS001002请求中的LENDER_ID',
    
    lenderName VARCHAR(16) NULL COMMENT '投资人的姓名，可为空，同user_main表realname字段一致，对应TRS001002请求中的LENDER_NAME',
    
    loanAmount DECIMAL(14,2) NOT NULL COMMENT '此Investor对某标的出借金额，同loan_investor表investAmount字段一致，对应TRS001002请求中的LOAN_AMOUNT',
    
    moneyRecordId INT UNSIGNED NULL COMMENT '此条投标记录关联的money_recored表中的id',

	returnCode CHAR(4) NULL COMMENT '首信易返回码',
	
	returnMsg VARCHAR(64) NULL COMMENT '首信易返回结果描述',
  	
	returnTime DATETIME NULL COMMENT '首信易返回时间',
	
	UNIQUE INDEX idx_payease_trs001002_serlNum (serlNum),
	
	INDEX idx_payease_trs001002_reqTime (reqTime),
	
	INDEX idx_payease_trs001002_operationCode (operationCode),
	
	INDEX idx_payease_trs001002_borrower (borrower),
	
	INDEX idx_payease_trs001002_lender (lender),
	
	INDEX idx_payease_trs001002_contractNum (contractNum)
  	
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*首信易  标的成功通知
 * */
CREATE TABLE payease_trs001003(

	msgid INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键', 
	
	reqTime DATETIME NOT NULL COMMENT '向首信易发送请求的时间',
 
	operationCode SMALLINT UNSIGNED NOT NULL COMMENT '首信易业务操作码',
	
	serlNum INT UNSIGNED NOT NULL COMMENT '系统交易流水唯一标示，从sequence_id表中取值',
	
	authId INT UNSIGNED NULL COMMENT '冻结号码唯一不允许重复，可为空。同loan_investor表id字段一致，对应TRS001003请求中的AUTH_ID',
	
	borrower INT UNSIGNED NOT NULL COMMENT '借款人的userId，同loan表borrowerId字段一致，对应TRS001003请求中的BORROWER',
	
	borrowerIdType CHAR(2) NULL COMMENT '借款人的证件类型，可为空，对应TRS001003请求中的BORROWER_IDTYPE',
	
	borrowerId CHAR(18) NULL COMMENT '借款人的证件号，可为空，同user_main表idNo字段一致，对应TRS001003请求中的BORROWER_ID',
	
	borrowerName VARCHAR(16) NULL COMMENT '借款人的姓名，可为空，同user_main表 realname字段一致，对应TRS001003请求中的BORROWER_NAME',

	contractNum INT UNSIGNED NOT NULL COMMENT '借款标的号（合同号）。这里用所投标的loanId代替，同loan_investor表loanId字段一致，对应TRS001003请求中的CONTRACT_NUM',
	
	totalAmount DECIMAL(14,2) NOT NULL COMMENT '借款总金额。同loan表biddingAmount字段一致，对应TRS001003请求中的TOTAL_AMOUNT',
	
	totalNum SMALLINT UNSIGNED NULL COMMENT '借款总笔数。对应TRS001003请求中的TOTAL_NUM',
	
	returnCode CHAR(4) NULL COMMENT '首信易返回码',
	
	returnMsg VARCHAR(64) NULL COMMENT '首信易返回结果描述',
  	
	returnTime DATETIME NULL COMMENT '首信易返回时间',
	
	UNIQUE INDEX idx_payease_trs001003_serlNum (serlNum),
	
	KEY idx_payease_trs001003_reqTime (reqTime),
	
	KEY idx_payease_trs001003_operationCode (operationCode),
	
	KEY idx_payease_trs001003_borrower (borrower),
	
	KEY idx_payease_trs001003_contractNum (contractNum)
  	
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*首信易  提现交易
 * 此表中所有response的结果都会更新到log_withdraw表的payeaseStatus字段
 * */
CREATE TABLE payease_trs001006(

	msgid INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键', 
	
	reqTime DATETIME NOT NULL COMMENT '向首信易发送请求的时间',
 
	operationCode SMALLINT UNSIGNED NOT NULL COMMENT '首信易业务操作码',
	
	serlNum INT UNSIGNED NOT NULL COMMENT '系统交易流水唯一标示，同money_record表id字段一致，对应TRS001007请求中的SERL_NUM',
	
	authId INT UNSIGNED NULL COMMENT '冻结号码唯一不允许重复，可为空。对应TRS001007请求中的AUTH_ID',
	
	user  INT UNSIGNED NOT NULL COMMENT '用户id，同log_withdraw表userid字段一致，对应SYN001006请求中的USER',
	
	accName VARCHAR(16) NOT NULL COMMENT '开户名，对应use_main表的realName，以及SYN001006请求中的ACC_NAME',
	
	accBankCode SMALLINT UNSIGNED NOT NULL COMMENT '开户行代码',
	
	accNum VARCHAR(25) NOT NULL COMMENT '银行账号，对应log_withdraw表的bankCardNo，以及SYN001006请求中的ACC_NUM',
	
	accType CHAR(2) NOT NULL COMMENT '账号类型，对应SYN001006请求中的ACC_TYPE',
	
	accProp CHAR(2) NOT NULL COMMENT '账号类别，对应SYN001006请求中的ACC_PROP',
	
	totalAmount DECIMAL(14,2) NOT NULL COMMENT '扣取虚拟账户资金。对应log_withdraw表的amount，以及TRS001006请求中的TOTAL_AMOUNT',
	
	amount DECIMAL(14,2) NOT NULL COMMENT '提现至银行卡金额。对应TRS001006请求中的AMOUNT',
	
	fee DECIMAL(14,2) NOT NULL COMMENT 'P2P平台收取提现用户的手续费。对应log_withdraw表的fee，以及TRS001006请求中的FEE',
	
	urgency CHAR(2) NOT NULL COMMENT '是否实时通道。对应TRS001006请求中的URGENCY',
	
	amountSplit CHAR(1) NOT NULL COMMENT '拆分标识。对应TRS001006请求中的AMOUNT_SPLIT',
	
	moneyRecordId INT UNSIGNED NULL COMMENT '此条提现记录关联的money_recored表中的id',

	returnCode CHAR(4) NULL COMMENT '首信易返回码',
	
	returnMsg VARCHAR(64) NULL COMMENT '首信易返回结果描述',
  	
	returnTime DATETIME NULL COMMENT '首信易返回时间',
	
	UNIQUE INDEX idx_payease_trs001006_serlNum (serlNum),
	
	INDEX idx_payease_trs001006_reqTime (reqTime),
	
	INDEX idx_payease_trs001006_operationCode (operationCode),

	INDEX idx_payease_trs001006_returnCode (returnCode),
	
	INDEX idx_payease_trs001006_transfer_in_user (user)
  	
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*首信易  转账交易
 * 此表中所有response的结果都会更新到money_recored表的payeaseStatus字段
 * */
CREATE TABLE payease_trs001007(

	msgid INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键', 
	
	reqTime DATETIME NOT NULL COMMENT '向首信易发送请求的时间',
 
	operationCode INT UNSIGNED NOT NULL COMMENT '首信易业务操作码，取值范围为OperationCode.TRS001007',
	
	serlNum INT UNSIGNED NOT NULL COMMENT '系统交易流水唯一标示，同money_record表id字段一致，对应TRS001007请求中的SERL_NUM',
	
	authId INT UNSIGNED NULL COMMENT '授权号，可为空。对应TRS001007请求中的AUTH_ID',
	
	transferOutUser INT UNSIGNED NOT NULL COMMENT '转出方的userId，同money_record的fromUserId一致，对应TRS001007请求中的TRANSFER_OUT_USER',
	
	transferOutUserId CHAR(18) NULL COMMENT '转出方的开户证件号（身份证号），对应TRS001007请求中的TRANSFER_OUT_USER_ID',
	
	transferInUser INT UNSIGNED NOT NULL COMMENT '转入方的userId，同money_record的toUserId一致，对应TRS001007请求中的TRANSFER_IN_USER',
	
	transferInUserId CHAR(18) NULL COMMENT '转入方的开户证件号（身份证号），对应TRS001007请求中的TRANSFER_IN_USER_ID',
	
	transferAmount DECIMAL(14,2) NOT NULL COMMENT '转账金额。同money_record的toUserId一致，对应TRS001007请求中的TOTAL_AMOUNT',
	
	transferOutUserFee DECIMAL(14,2) NOT NULL COMMENT '转出方手续费。对应TRS001007请求中的TRANSFER_OUT_USER_FEE',
	
	transferInUserFee DECIMAL(14,2) NOT NULL COMMENT '转入方手续费。对应TRS001007请求中的TRANSFER_IN_USER_FEE',
	
	moneyRecordId INT UNSIGNED NULL COMMENT '此条转账记录关联的money_recored表中的id',
	
	returnCode CHAR(4) NULL COMMENT '首信易返回码',
	
	returnMsg VARCHAR(64) NULL COMMENT '首信易返回结果描述',
  	
	returnTime DATETIME NULL COMMENT '首信易返回时间',
	
	UNIQUE INDEX idx_payease_trs001007_serl_num (serlNum),
	
	INDEX idx_payease_trs001007_reqTime (reqTime),

	INDEX idx_payease_trs001007_returnCode (returnCode),
	
	INDEX idx_payease_trs001007_transfer_out_user (transferOutUser),
	
	INDEX idx_payease_trs001007_transfer_in_user (transferInUser)
  	
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*
 * 首信易  代扣交易
 * */
CREATE TABLE payease_trs001008(

	msgid INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '主键', 
	
	reqTime DATETIME NOT NULL COMMENT '向首信易发送请求的时间',
 
	operationCode INT UNSIGNED NOT NULL COMMENT '首信易业务操作码',
	
	serlNum INT UNSIGNED NOT NULL COMMENT '系统交易流水唯一标示，同money_record表id字段一致，对应TRS001008请求中的SERL_NUM',
	
	user INT UNSIGNED NOT NULL COMMENT '用户id，对应TRS001008请求中的USER',
	
	idType CHAR(2) NOT NULL COMMENT '证件类型，对应TRS001008请求中的ID_TYPE',
	
	id CHAR(18) NOT NULL COMMENT '用户证件号，对应use_main表的idNo，以及TRS001008请求中的id',
	
	accName VARCHAR(16) NOT NULL COMMENT '开户名，对应use_main表的realName，以及SYN001008请求中的ACC_NAME',
	
	accNum VARCHAR(25) NOT NULL COMMENT '银行账号，对应bank_card表的cardNo，以及SYN001008请求中的ACC_NUM',

	accBankCode SMALLINT UNSIGNED NOT NULL COMMENT '开户行代码',
	
	accType CHAR(2) NOT NULL COMMENT '账号类型，对应SYN001008请求中的ACC_TYPE',
	
	accProp CHAR(2) NOT NULL COMMENT '账号类别，对应SYN001008请求中的ACC_PROP',
	
	amount DECIMAL(14,2) NOT NULL COMMENT '代扣至虚拟账户的金额。对应TRS001008请求中的AMOUNT',
	
	merdata1 VARCHAR(16) NULL COMMENT '预留字段1',
	
	moneyRecordId INT UNSIGNED NULL COMMENT '此条代扣记录关联的money_recored表中的id',
	
	returnCode CHAR(4) NULL COMMENT '首信易返回码',
	
	returnMsg VARCHAR(64) NULL COMMENT '首信易返回结果描述',
  	
	returnTime DATETIME NULL COMMENT '首信易返回时间',
	
	UNIQUE INDEX idx_payease_trs001008_serlNum (serlNum),
	
	INDEX idx_payease_trs001008_reqTime (reqTime),
	
	INDEX idx_payease_trs001008_accNum (accNum),
	
	INDEX idx_payease_trs001008_returnCode (returnCode),
	
	INDEX idx_payease_trs001008_user (user)
  	
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*
 *首信易  身份验证查询
 * */
CREATE TABLE payease_idcard_verify(

	name VARCHAR(16) NOT NULL COMMENT '验证的用户姓名',

	idcardNo VARCHAR(18) NOT NULL COMMENT '验证的用户身份证号',

	status SMALLINT(1) UNSIGNED NOT NULL COMMENT '验证是否存在：2：用户不存在； 1：用户存在',

	verifyTime DATETIME NOT NULL COMMENT '身份验证时间',

	UNIQUE INDEX idx_payease_idcard_verify_name_idcard_no (name,idcardNo)

)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*
 * 首信易  网上支付
 * */
CREATE TABLE payease_recharge(

	v_oid VARCHAR(32) NOT NULL COMMENT '订单编号：不可为空值，首信易支付订单编号格式统一为：订单生成日期（yyyymmdd）-商户编号-商户流水号',

	v_rcvname VARCHAR(32) NOT NULL COMMENT '收货人姓名：不可为空值，考虑到系统编码可能不统一的问题，建议统一用商户编号的值代替',

	v_rcvaddr VARCHAR(128) NOT NULL COMMENT '收货人地址：不可为空值，总长不超过128个字符，考虑到系统编码可能不统一的问题，建议统一用商户编号的值代替',

	v_rcvtel VARCHAR(32) NOT NULL COMMENT '收货人电话：不可为空值，总长不超过32个字符，建议统一用商户编号的值代替',

	v_rcvpost VARCHAR(10) NOT NULL COMMENT '收货人邮政编码：不可为空值，总长不超过10个字符，建议统一用商户编号的值代替',

	v_amount DECIMAL(14,2) NOT NULL COMMENT '订单总金额：不可为空值，单位：元，小数点后保留两位，如13.45',

	v_ymd VARCHAR(8) NOT NULL COMMENT '订单产生日期：不可为空值，长度为8位，格式为yyyymmdd，例如：20100101',

	v_orderstatus CHAR(1) NOT NULL COMMENT '配货状态：商户配货状态，0为未配齐，1为已配齐；一般商户该参数无实际意义，建议统一配置为1的状态',

	v_ordername VARCHAR(64) NOT NULL COMMENT '订货人姓名：总长不超过64个字符，考虑到系统编码可能不统一的问题，建议统一用商户编号的值代替',

	v_moneytype INT UNSIGNED NOT NULL COMMENT '支付币种：0为人民币',

	v_pmode INT UNSIGNED NULL COMMENT '支付方式编号：可以为空',

	v_merdata1 VARCHAR(32) NULL COMMENT '商户自定义参数1：此参数可选，传递会员ID，必须采用UTF-8字符的URL编码格式',

	v_merdata2 VARCHAR(32) NULL COMMENT '商户自定义参数2：此参数可选，传递证件类型，必须采用UTF-8字符的URL编码格式',

	v_merdata3 VARCHAR(32) NULL COMMENT '商户自定义参数3：此参数可选，传递证件号码，必须采用UTF-8字符的URL编码格式',

	v_merdata4 VARCHAR(32) NULL COMMENT '商户自定义参数4：此参数可选，传递用户真实姓名，必须采用UTF-8字符的URL编码格式',

	v_merdata5 VARCHAR(32) NULL COMMENT '商户自定义参数5：此参数可选，传递用户注册手机号码，必须采用UTF-8字符的URL编码格式',

	v_merdata6 VARCHAR(32) NULL COMMENT '商户自定义参数6：此参数可选，传递备注信息1，必须采用UTF-8字符的URL编码格式',

	v_merdata7 VARCHAR(32) NULL COMMENT '商户自定义参数7：此参数可选，传递备注信息2，必须采用UTF-8字符的URL编码格式',

	v_pstatus SMALLINT UNSIGNED NULL COMMENT '1(已提交，对使用非实时银行卡进行扣款的订单)；20（支付成功，对使用实时银行卡进行扣款的订单）；30（支付失败，对使用实时银行卡进行扣款的订单）',

	v_pstring VARCHAR(32) NULL COMMENT '支付结果信息',

	UNIQUE INDEX idx_payease_recharge_v_oid (v_oid),

	INDEX idx_payease_recharge_v_pstatus (v_pstatus)

)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*模拟oracle的sequence id，用来给首信易操作生成一个全局唯一的id号*/
CREATE TABLE payease_sequence_id (

  id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY
  
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/********************************************银生宝相关表********************************************/

/*
 * 银生宝子协议表
 * */
CREATE TABLE unspay_sub_contract(

	id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键，无业务含义',

	filename VARCHAR(32) NOT NULL COMMENT '导入文件名',

	uploadDate DATE NOT NULL COMMENT '导入时间',

	operator INT UNSIGNED NOT NULL COMMENT '操作人ID,上传人',

	loanApplyId BIGINT UNSIGNED NOT NULL COMMENT '进件编号，与导入者的业务有关，保证唯一性',

	name VARCHAR(32) NOT NULL COMMENT '客户姓名',

	idCardNo VARCHAR(18) NOT NULL COMMENT '客户身份证号',

	cardNo VARCHAR(21) NOT NULL COMMENT '客户银行卡号',

	bankCode SMALLINT(3) NOT NULL DEFAULT 999 COMMENT '银行代码',

	phoneNo BIGINT(11) UNSIGNED NOT NULL COMMENT '用户手机号',

	startDate DATE NOT NULL COMMENT '子协议开始时间',

	endDate DATE NOT NULL COMMENT '子协议结束时间',

	cycle SMALLINT(1) UNSIGNED NULL COMMENT '扣款频率：1：每年； 2：每月； 3：每日',

	triesLimit INT UNSIGNED NULL COMMENT '扣款次数限制',

	sendDate DATETIME NULL COMMENT '子协议发送时间',

	signStatus VARCHAR(4) NULL COMMENT '子协议签署状态',

	signMsg VARCHAR(64) NULL COMMENT '子协议签署返回消息',

	subContractId VARCHAR(32) NULL COMMENT '子协议号，在调用子协议录入接口后返回或手动查询',

	UNIQUE idx_unspay_sub_contract_loan_apply_id (loanApplyId)

)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*
 * 银生宝代付表
 * */
CREATE TABLE unspay_pay(

	orderId INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键，作为具有唯一性的订单号',

	filename VARCHAR(32) NOT NULL COMMENT '导入文件名',

	uploadDate DATE NOT NULL COMMENT '导入时间',

	operator INT UNSIGNED NOT NULL COMMENT '操作人ID，上传人',

	auditor INT UNSIGNED NULL COMMENT '操作人ID,审核人',

	loanApplyId BIGINT UNSIGNED NOT NULL COMMENT '进件编号，与导入者的业务有关，保证唯一性',

	name VARCHAR(32) NOT NULL COMMENT '收款人姓名',

	cardNo VARCHAR(21) NOT NULL COMMENT '收款人银行卡号',

	amount DECIMAL(14,2) NOT NULL COMMENT '扣款金额',

	purpose VARCHAR(32) NOT NULL COMMENT '扣款目的',

	verifyStatus SMALLINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '审核状态，0：未审核 1：审核通过 2：审核拒绝',

	planDate DATETIME NULL COMMENT '代扣计划发送时间，没有则立即发送',

	sendDate DATETIME NULL COMMENT '代扣发送时间',

	responseDate DATETIME NULL COMMENT '代扣返回时间',

	payResult VARCHAR(4) NULL COMMENT '付款结果，银生宝通过回调地址返回扣款结果或主动查询（交易状态： 00，成功； 10，处理中； 20，失败；）',

	`desc` VARCHAR(64) NULL COMMENT '交易结果描述信息（主动查询获取）',

	INDEX idx_unspay_pay_loan_apply_id (loanApplyId)

)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*
 * 银生宝代扣表
 * */
CREATE TABLE unspay_deduct(

	orderId INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键，作为具有唯一性的订单号',

	filename VARCHAR(32) NOT NULL COMMENT '导入文件名',

	uploadDate DATE NOT NULL COMMENT '导入时间',

	operator INT UNSIGNED NOT NULL COMMENT '操作人ID，上传人',

	auditor INT UNSIGNED NULL COMMENT '操作人ID,审核人',

	loanApplyId BIGINT UNSIGNED NOT NULL COMMENT '进件编号，与导入者的业务有关，保证唯一性',

	repayPhaseId BIGINT UNSIGNED NULL COMMENT '分期ID编号',

	subContractId VARCHAR(32) NOT NULL COMMENT '子协议编号',

	amount DECIMAL(14,2) NOT NULL COMMENT '扣款金额',

	purpose VARCHAR(32) NOT NULL COMMENT '扣款目的',

	verifyStatus SMALLINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '审核状态，0：未审核 1：审核通过 2：审核拒绝',

	planDate DATETIME NULL COMMENT '代扣计划发送时间，没有则立即发送',

	extra VARCHAR(32) NULL COMMENT '附加字段',

	sendDate DATETIME NULL COMMENT '代扣发送时间',

	responseDate DATETIME NULL COMMENT '代扣返回时间',

	deductResult VARCHAR(4) NULL COMMENT '扣款结果，银生宝通过回调地址返回扣款结果或主动查询（交易状态： 00，成功； 10，处理中； 20，失败；）',

	`desc` VARCHAR(64) NULL COMMENT '交易结果描述信息（主动查询获取）',

	INDEX idx_unspay_deduct_sub_contrac_id (subContractId),

	INDEX idx_unspay_deduct_loan_apply_id (loanApplyId)

)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*
 * 中城国典银生宝实时代付表
 * */
CREATE TABLE unspay_zcgd_pay(

	orderId INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键，作为具有唯一性的订单号',

	filename VARCHAR(32) NOT NULL COMMENT '导入文件名',

	uploadDate DATE NOT NULL COMMENT '导入时间',

	operator INT UNSIGNED NOT NULL COMMENT '操作人ID，上传人',

	auditor INT UNSIGNED NULL COMMENT '操作人ID,审核人',

	loanApplyId BIGINT UNSIGNED NOT NULL COMMENT '进件编号，与导入者的业务有关，保证唯一性',

	name VARCHAR(32) NOT NULL COMMENT '收款人姓名',

	cardNo VARCHAR(21) NOT NULL COMMENT '收款人银行卡号',

	amount DECIMAL(14,2) NOT NULL COMMENT '金额',

	purpose VARCHAR(32) NOT NULL COMMENT '付款目的',
	
	idCardNo VARCHAR(18) NOT NULL COMMENT '收款人身份证号',
	
	summary VARCHAR(32) NOT NULL COMMENT '付款人付款摘要',
	
	phoneNo BIGINT(11) UNSIGNED NULL COMMENT '收款人手机号',

	verifyStatus SMALLINT(1) UNSIGNED NOT NULL DEFAULT 0 COMMENT '审核状态，0：未审核 1：审核通过 2：审核拒绝',

	sendDate DATETIME NULL COMMENT '代付发送时间',

	responseDate DATETIME NULL COMMENT '代付返回时间',

	payResult VARCHAR(4) NULL COMMENT '付款结果，银生宝通过回调地址返回扣款结果或主动查询（交易状态： 00，成功； 10，处理中； 20，失败；）',

	`desc` VARCHAR(64) NULL COMMENT '交易结果描述信息（主动查询获取）',

	INDEX idx_unspay_pay_loan_apply_id (loanApplyId)

)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/********************************************畅捷相关表********************************************/
/*
 * 畅捷支付
 * */
CREATE TABLE chanpay_q20003_bean(

	outTradeNo VARCHAR(32) NOT NULL COMMENT '畅捷支付合作商户网站唯一订单号（确保在商户系统中唯一）',

	tradeAmount DECIMAL(14,2) NOT NULL COMMENT '交易金额',

  actionDesc VARCHAR(200) COMMENT '交易描述 JSON字符串 可放透传的信息。存放原订单号和原订单付款金额等信息',

	orderTime DATETIME COMMENT '商户订单提交时间',

	buyerMobile BIGINT UNSIGNED COMMENT '买家手机号',

	buyerIp VARCHAR(32) COMMENT '用户在商户平台下单时候的ip地址',

	payerBankname VARCHAR(300) COMMENT '付款方银行名称（详细到支行）',

	payerBankaccountNo VARCHAR(19) COMMENT '付款方银行账号',

	ext1 VARCHAR(300) COMMENT '扩展字段',

	ext2 VARCHAR(300) COMMENT '扩展字段',

	INDEX idx_chanpay_q20003_bean_out_trade_no (outTradeNo)

)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 畅捷交易状态通知
 */
CREATE TABLE chanpay_q20008_bean(

	notifyId VARCHAR(32) NOT NULL COMMENT '通知的唯一标识',

	notifyType VARCHAR(32) NOT NULL COMMENT '交易通知此字段为：trade_status_sync',

	notifyTime DATETIME NOT NULL COMMENT '知的发送时间，格式：yyyyMMddHHmmss',

  inputCharset VARCHAR(32) COMMENT '参数字符集编码',

	sign VARCHAR(256) NOT NULL COMMENT '签名',

	signType VARCHAR(3) NOT NULL COMMENT '签名方式只支持RSA、MD5不可空',

	version DOUBLE(2,1) NOT NULL COMMENT '接口版本，目前只有固定值1.0,不可空',

	outerTradeNo VARCHAR(32) NOT NULL COMMENT '商户网站唯一订单号,订单支付中的一笔订单号',

	innerTradeNo VARCHAR(32) NOT NULL COMMENT '支付平台交易订单号',

	tradeStatus VARCHAR(16) NOT NULL COMMENT '交易状态',

	tradeAmount DECIMAL(14,2) NOT NULL COMMENT '交易金额',

	gmtCreate DATETIME NOT NULL COMMENT '交易创建时间',

	gmtPayment DATETIME COMMENT '交易支付时间',

	gmtClose DATETIME COMMENT '交易关闭时间',

	extension VARCHAR(100) COMMENT '扩展参数',

	INDEX idx_chanpay_q20008_bean_outre_trade_no (outerTradeNo)

)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 畅捷通付款表
 */
CREATE TABLE chanpay_g10002_bean(

	reqSn VARCHAR(32) NOT NULL COMMENT '交易请求号 数据格式：(15位)商户号 + (12位)yyMMddHHmmss时间戳 + (5位)循环递增序号 = (32位)唯一交易号',

	timestamp DATETIME NOT NULL COMMENT '提交时间',

	bankGeneralName VARCHAR(60) NOT NULL COMMENT '银行通用名称',

	accountNo VARCHAR(32) NOT NULL COMMENT '账号',

	accountName VARCHAR(60) NOT NULL COMMENT '账户名称',

	bankName VARCHAR(60) NOT NULL COMMENT '开户行名称',

	amount DECIMAL(14,2) NOT NULL COMMENT '金额',

	idType VARCHAR(1) COMMENT '开户证件类型',

	id VARCHAR(22) COMMENT '证件号',

	tel VARCHAR(11) COMMENT '手机号',

	corpFlowNo VARCHAR(32) COMMENT '企业自己系统的业务流水号',

	summary VARCHAR(140) COMMENT '备注',

	postscript VARCHAR(256) COMMENT '用途',

	retCode VARCHAR(4) COMMENT '返回代码',

	errMsg VARCHAR(512) COMMENT '错误信息',

	tradeCode VARCHAR(4) COMMENT '交易结果代码',

	tradeMsg VARCHAR(512) COMMENT '交易错误信息',

	INDEX idx_chanpay_g10002_bean_req_sn (reqSn),

	INDEX idx_chanpay_g10002_bean_account_no (accountNo)

)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 畅捷通实时交易结果查询
 */
CREATE TABLE chanpay_g20001_bean(

	reqSn VARCHAR(32) NOT NULL COMMENT '交易请求号 数据格式：(15位)商户号 + (12位)yyMMddHHmmss时间戳 + (5位)循环递增序号 = (32位)唯一交易号',

	timestamp DATETIME NOT NULL COMMENT '提交时间',

	qryReqSn VARCHAR(32) NOT NULL COMMENT '要查询的交易请求号',

	charge DECIMAL(14,2) COMMENT '手续费',

	accountNo VARCHAR(32) NOT NULL COMMENT '对方账号',

	accountName VARCHAR(60) NOT NULL COMMENT '对方账户名称',

	amount DECIMAL(14,2) NOT NULL COMMENT '金额',

	corpFlowNo VARCHAR(32) COMMENT '企业自己系统的业务流水号',

	summary VARCHAR(140) COMMENT '备注',

	postscript VARCHAR(256) COMMENT '用途',

	retCode VARCHAR(4) COMMENT '业务返回代码',

	errMsg VARCHAR(512) COMMENT '业务返回码描述',

	INDEX idx_chanpay_g10002_bean_qry_req_sn (qryReqSn)

)ENGINE=InnoDB DEFAULT CHARSET=utf8;
/**
 * 畅捷通付款表回调表
 */
CREATE TABLE chanpay_g20014_bean(

	reqSn VARCHAR(32) NOT NULL COMMENT '交易请求号 数据格式：(15位)商户号 + (12位)yyMMddHHmmss时间戳 + (5位)循环递增序号 = (32位)唯一交易号',

	timestamp DATETIME NOT NULL COMMENT '提交时间',

	trxReqSn VARCHAR(32) NOT NULL COMMENT '原交易请求号',

	retCode VARCHAR(4) COMMENT '业务返回代码',

	errMsg VARCHAR(512) COMMENT '业务返回码描述',

	corpAcctNo VARCHAR(32) NOT NULL COMMENT '企业账号',

	accountNo VARCHAR(32) NOT NULL COMMENT '账号',

	accountName VARCHAR(60) NOT NULL COMMENT '账户名称',

	protocolNo VARCHAR(64) COMMENT '协议号 企业客户与付款人签署的收款协议',

	amount DECIMAL(14,2) NOT NULL COMMENT '金额',

	charge DECIMAL(14,2) COMMENT '手续费',

	corpFlowNo VARCHAR(50) COMMENT '外部企业流水号',

	summary VARCHAR(140) COMMENT '备注',

	postscript VARCHAR(256) COMMENT '用途',

	INDEX idx_chanpay_g10002_bean_trx_req_sn (trxReqSn)

)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 畅捷通行名、行号表
 */
CREATE TABLE chanpay_g40001_bean(

	sn INTEGER NOT NULL COMMENT '序号',

	bankCode VARCHAR(3) NOT NULL COMMENT '银行代码',

	accountBankCode VARCHAR(12) NOT NULL COMMENT '支付行号',

	clearingBankCode VARCHAR(12) NOT NULL COMMENT '支付行清算行号',

	accountBankName VARCHAR(128) NOT NULL COMMENT '开户行名称',

	regionCode INTEGER(12) NOT NULL COMMENT '地区码',

	secondGeneration VARCHAR(1) NOT NULL COMMENT '是否支持二代业务',

	INDEX idx_chanpay_g40001_bean_account_bank_name (accountBankName),

	INDEX idx_chanpay_g40001_bean_account_bank_code (accountBankCode)

)ENGINE=InnoDB DEFAULT CHARSET=utf8;
/**
 * 身份认证请求
 */
CREATE TABLE chanpay_g60001_bean(

	reqSn VARCHAR(32) NOT NULL COMMENT '交易请求号 数据格式：(15位)商户号 + (12位)yyMMddHHmmss时间戳 + (5位)循环递增序号 = (32位)唯一交易号',

	timestamp DATETIME NOT NULL COMMENT '提交时间',

	sn VARCHAR(40) NOT NULL COMMENT '明细号',

	bankGeneralName VARCHAR(60) NOT NULL COMMENT '银行通用名称',

	bankName VARCHAR(60) COMMENT '开户行名称',

	bankCode VARCHAR(12) COMMENT '开户行号',

	accountType VARCHAR(2) NOT NULL COMMENT '账号类型',

	accountNo VARCHAR(32) NOT NULL COMMENT '账号',

	accountName VARCHAR(60) NOT NULL COMMENT '账户名称',

	idType VARCHAR(1) NOT NULL COMMENT '开户证件类型',

	id VARCHAR(22) COMMENT '证件号',

	tel VARCHAR(11) COMMENT '手机号',

	retCode VARCHAR(4) COMMENT '返回代码',

	errMsg VARCHAR(512) COMMENT '错误信息',

	INDEX idx_chanpay_g60001_bean_req_sn (reqSn)

)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 身份认证查询结果
 */
CREATE TABLE chanpay_g60002_bean(

	reqSn VARCHAR(32) NOT NULL COMMENT '交易请求号 数据格式：(15位)商户号 + (12位)yyMMddHHmmss时间戳 + (5位)循环递增序号 = (32位)唯一交易号',

	timestamp DATETIME NOT NULL COMMENT '提交时间',

	qryReqSn VARCHAR(32) NOT NULL COMMENT '要查询交易的原请求号',

	batchQryReqSn VARCHAR(32) COMMENT '批次：要查询交易的原请求号',

	batchRetCode VARCHAR(4) COMMENT '批次：返回代码',

	batchErrMsg VARCHAR(512) COMMENT '批次：错误信息',

	dtlsn VARCHAR(40) COMMENT '明细号',

	dtlRetCode VARCHAR(4) COMMENT '协议的业务返回码',

	dtlErrMsg VARCHAR(512) COMMENT '协议的业务返回码描述',

	dtlaccountNo VARCHAR(32) COMMENT '账号',

	dtlaccountName VARCHAR(60) COMMENT '账户名称',

	retCode VARCHAR(4) COMMENT '返回代码',

	errMsg VARCHAR(512) COMMENT '错误信息',

	INDEX idx_chanpay_g60002_bean_qry_req_sn (qryReqSn)

)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 身份同步认证
 */
CREATE TABLE chanpay_g60009_bean(

	reqSn VARCHAR(32) NOT NULL COMMENT '交易请求号 数据格式：(15位)商户号 + (12位)yyMMddHHmmss时间戳 + (5位)循环递增序号 = (32位)唯一交易号',

	timestamp DATETIME NOT NULL COMMENT '提交时间',

	smsCode VARCHAR(6) COMMENT '短信验证码',

	orgReqSn VARCHAR(32) COMMENT '原始交易（短信请求）请求号',

	subMerchantId VARCHAR(40) NOT NULL COMMENT '二级商户代码',

	sn VARCHAR(40) NOT NULL COMMENT '明细号',

	bankGeneralName VARCHAR(60) NOT NULL COMMENT '银行通用名称',

	bankName VARCHAR(60) COMMENT '开户行名称',

	bankCode VARCHAR(12) NOT NULL COMMENT '开户行号',

	accountType VARCHAR(2) NOT NULL COMMENT '账号类型',

	accountNo VARCHAR(32) NOT NULL COMMENT '账号',

	accountName VARCHAR(60) NOT NULL COMMENT '账户名称',

	idType VARCHAR(1) NOT NULL COMMENT '开户证件类型',

	id VARCHAR(22) COMMENT '证件号',

	tel BIGINT(11) UNSIGNED COMMENT '手机号',

	retCode VARCHAR(4) COMMENT '返回代码',

	errMsg VARCHAR(512) COMMENT '错误信息',

	INDEX idx_chanpay_g60009_bean_req_sn (reqSn)

)ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 畅捷通自增ID号，用于生成交易序列号 循环递增序号
 */
CREATE TABLE chanpay_sequence_id (

	id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY

) ENGINE=InnoDB DEFAULT CHARSET=utf8;




/*------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                       华兴银行                                                                                                                                       */
/*------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

/**
 * 华兴银行自增ID号，用于生成唯一交易序列号
 */
CREATE TABLE ghbank_sequence_id (

	id INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 短信验证码(OGW00041)
 */
CREATE TABLE ghbank_ogw00041(

    priority SMALLINT NOT NULL DEFAULT 1 COMMENT '优先级 1：普通 2：紧急 3：特急',

    invokeMethod SMALLINT NOT NULL DEFAULT 1 COMMENT '调用方式 1：同步 2：异步（需主动回查）',

    channelFlow CHAR(28) NOT NULL COMMENT '同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）',

    channelDateTime DATETIME NOT NULL COMMENT '渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期',

	transCode CHAR(8) NOT NULL COMMENT '交易码',

    appId CHAR(3) NOT NULL DEFAULT 'PC' COMMENT '应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX',

    trsType SMALLINT NOT NULL DEFAULT 0 COMMENT '操作类型。1：自动投标撤销 2：自动还款授权撤销 0：默认',

    acNo VARCHAR(32) COMMENT 'E账户账号，即关联账号.P2p商户必填',

    mobileNo BIGINT(11) COMMENT '注册的时候必填，11位手机号',

    otpSeqNo VARCHAR(30) COMMENT '短信唯一标识',

    otpIndex VARCHAR(30) COMMENT '短信序号',

	serverFlow CHAR(20) COMMENT '服务流水号',

	serverDateTime DATETIME COMMENT '服务日期 + 服务时间',

	status SMALLINT COMMENT '业务状态',

	errorCode VARCHAR(12) COMMENT '错误代码',

	errorMsg VARCHAR(300) COMMENT '错误信息,错误代码非0时，返回具体的错误原因',

	extFiled1 VARCHAR(200) COMMENT '备用字段1',

	extFiled2 VARCHAR(200) COMMENT '备用字段2',

	extFiled3 VARCHAR(200) COMMENT '备用字段3',
	
	INDEX idx_ghbank_ogw00041_channelDateTime (channelDateTime),
	
	INDEX idx_ghbank_ogw00041_acNo (acNo),
	
	INDEX idx_ghbank_ogw00041_mobileNo (mobileNo)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 6.2	账户开立(OGW00042)
 */
CREATE TABLE ghbank_ogw00042(

    msgid INT UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY COMMENT '自增主键，无业务含义',
    
    /*--- 以下为Request 报文头 部分  ---*/

    priority SMALLINT NOT NULL DEFAULT 1 COMMENT '优先级 1：普通 2：紧急 3：特急',

    invokeMethod SMALLINT NOT NULL DEFAULT 1 COMMENT '调用方式 1：同步 2：异步（需主动回查）',

    channelFlow CHAR(28) NOT NULL COMMENT '同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）',

    channelDateTime DATETIME NOT NULL COMMENT '渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期',

	transCode CHAR(8) NOT NULL COMMENT '交易码',
	
	/*--- 以下为Request 报文体 部分。部分不会变动的字段如MerchantId, MerchantName, ReturnURL等，没有记录  ---*/

    appId CHAR(3) NOT NULL DEFAULT 'PC' COMMENT '应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX',

    tTrans SMALLINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '交易类型',

    acName VARCHAR(128) COMMENT '姓名',

    idType INT UNSIGNED DEFAULT 1010 COMMENT '证件类型，目前只能是身份证',

    idNo CHAR(18) COMMENT '证件号码,只支持身份证',

    mobile BIGINT UNSIGNED COMMENT '手机号码',

    email VARCHAR(64) COMMENT '用户邮箱',

    custMngrNo VARCHAR(50) COMMENT '客户经理编号',

    /*--- 以下为Response 报文头 部分  ---*/
    
	serverFlow VARCHAR(20) COMMENT '服务流水号',

	serverDateTime DATETIME COMMENT '服务日期 + 服务时间',

	status SMALLINT COMMENT '业务状态',

	errorCode VARCHAR(12) COMMENT '错误代码',

	errorMsg VARCHAR(300) COMMENT '错误信息,错误代码非0时，返回具体的错误原因',
	
	/*--- 以下为Response 报文体 部分  ---*/
	
	oldReqSeqNo CHAR(32) COMMENT '原开户交易流水号',
	
    respAcName VARCHAR(32) COMMENT '客户姓名',
    
    respIdType INT UNSIGNED COMMENT '证件类型，目前只能是身份证',
    
    respIdNo VARCHAR(18) COMMENT '证件号码,只支持身份证',
    
    respMobile BIGINT UNSIGNED COMMENT '手机号码',
    
    acNo VARCHAR(32) COMMENT '华兴银行e账户账号',
    
	/*--- 以下为好时代再次向银行发送Req 报文体 部分  ---*/
	
    returnTransCode CHAR(8) COMMENT '交易码，本例中应为OGWR0001',
    
    returnCode CHAR(6) COMMENT '响应码，000000标识成功',

    returnMsg VARCHAR(128) COMMENT '响应信息',

    extFiled1 VARCHAR(200) COMMENT '备用字段1',

    extFiled2 VARCHAR(200) COMMENT '备用字段2',

    extFiled3 VARCHAR(200) COMMENT '备用字段3',
    
    INDEX idx_ghbank_ogw00042_channelFlow (channelFlow),
    
    INDEX idx_ghbank_ogw00042_channelDateTime (channelDateTime),
    
    INDEX idx_ghbank_ogw00042_idNo (idNo),
    
    INDEX idx_ghbank_ogw00042_acName (acName),
    
    INDEX idx_ghbank_ogw00042_mobile (mobile),
    
    INDEX idx_ghbank_ogw00042_acNo (acNo)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 6.3	账户开立结果查询(OGW00043)
 */
CREATE TABLE ghbank_ogw00043(

	priority SMALLINT NOT NULL DEFAULT 1 COMMENT '优先级 1：普通 2：紧急 3：特急',

	invokeMethod SMALLINT NOT NULL DEFAULT 1 COMMENT '调用方式 1：同步 2：异步（需主动回查）',

	channelFlow CHAR(28) NOT NULL PRIMARY KEY COMMENT '同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）',

	channelDateTime DATETIME NOT NULL COMMENT '渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期',

	transCode CHAR(8) NOT NULL COMMENT '交易码',

	appId CHAR(3) NOT NULL DEFAULT 'PC' COMMENT '应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX',

	oldReqSeqNo VARCHAR(28) NOT NULL COMMENT '原交易流水号',

	serverFlow CHAR(20) COMMENT '服务流水号',

	serverDateTime DATETIME COMMENT '服务日期 + 服务时间',

	status SMALLINT COMMENT '业务状态',

	errorCode VARCHAR(12) COMMENT '错误代码',

	errorMsg VARCHAR(300) COMMENT '错误信息,错误代码非0时，返回具体的错误原因',

	returnStatus CHAR(1) COMMENT '交易状态',

	returnErrorMsg VARCHAR(32) COMMENT '失败原因',

	resJnlNo VARCHAR(32) COMMENT '银行交易流水号',

	transDateTime DATETIME COMMENT '交易日期 + 交易时间',

	acNo VARCHAR(32) COMMENT '银行账号',

	acName VARCHAR(128) COMMENT '姓名',

	idType INT UNSIGNED DEFAULT 1010 COMMENT '证件类型，目前只能是身份证',

	idNo VARCHAR(18) COMMENT '证件号码,只支持身份证',

	mobile BIGINT UNSIGNED COMMENT '手机号码',

	extFiled1 VARCHAR(200) COMMENT '备用字段1',

	extFiled2 VARCHAR(200) COMMENT '备用字段2',

	extFiled3 VARCHAR(200) COMMENT '备用字段3',
	
	INDEX idx_ghbank_ogw00043_channelDateTime (channelDateTime),
	
	INDEX idx_ghbank_ogw00043_status (status),
	
	INDEX idx_ghbank_ogw00043_acName (acName),
	
	INDEX idx_ghbank_ogw00043_idType (idType)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 6.4	绑卡(OGW00044)
 */
CREATE TABLE ghbank_ogw00044(

    /*--- 以下为Request 报文头 部分  ---*/

	priority SMALLINT NOT NULL DEFAULT 1 COMMENT '优先级 1：普通 2：紧急 3：特急',

	invokeMethod SMALLINT NOT NULL DEFAULT 1 COMMENT '调用方式 1：同步 2：异步（需主动回查）',

	channelFlow CHAR(28) NOT NULL PRIMARY KEY COMMENT '同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）',

	channelDateTime DATETIME NOT NULL COMMENT '渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期',

	transCode CHAR(8) NOT NULL COMMENT '交易码',
	
	/*--- 以下为Request 报文体 部分  ---*/

	appId CHAR(3) NOT NULL DEFAULT 'PC' COMMENT '应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX',

	tTrans SMALLINT NOT NULL DEFAULT 0 COMMENT '交易类型',

	acNo VARCHAR(32) COMMENT '银行账号',
	
	/*--- 以下为Response 报文头 部分  ---*/

	serverFlow CHAR(20) COMMENT '服务流水号',

	serverDateTime DATETIME COMMENT '服务日期 + 服务时间',

	status SMALLINT COMMENT '业务状态',

	errorCode VARCHAR(12) COMMENT '错误代码',

	errorMsg VARCHAR(300) COMMENT '错误信息,错误代码非0时，返回具体的错误原因',
	
	/*--- 以下为Response 报文体 部分  ---*/
	
	returnCode VARCHAR(64) COMMENT '响应码',
	
	returnMsg VARCHAR(128) COMMENT '响应信息',

	extFiled1 VARCHAR(200) COMMENT '备用字段1',

	extFiled2 VARCHAR(200) COMMENT '备用字段2',

	extFiled3 VARCHAR(200) COMMENT '备用字段3',
	
	INDEX idx_ghbank_ogw00044_acNo (acNo)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 6.5	单笔专属账户充值(OGW00045)
 */
CREATE TABLE ghbank_ogw00045(

    /*--- 以下为Request 报文头 部分  ---*/

    priority SMALLINT NOT NULL DEFAULT 1 COMMENT '优先级 1：普通 2：紧急 3：特急',

    invokeMethod SMALLINT NOT NULL DEFAULT 1 COMMENT '调用方式 1：同步 2：异步（需主动回查）',

    channelFlow CHAR(28) NOT NULL PRIMARY KEY COMMENT '同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）',

    channelDateTime DATETIME NOT NULL COMMENT '渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期',

	transCode CHAR(8) NOT NULL COMMENT '交易码',
	
	/*--- 以下为Request 报文体 部分  ---*/

    appId CHAR(3) NOT NULL DEFAULT 'PC' COMMENT '应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX',

    tTrans SMALLINT NOT NULL DEFAULT 0 COMMENT '交易类型',

    acNo VARCHAR(32) NOT NULL COMMENT '银行账号',

    acName VARCHAR(128) NOT NULL COMMENT '账号户名',

    amount DECIMAL(15,2) NOT NULL COMMENT '交易金额',

	remark VARCHAR(128) COMMENT '备注',
	
	/*--- 以下为Response 报文头 部分  ---*/

	serverFlow CHAR(20) COMMENT '服务流水号',

	serverDateTime DATETIME COMMENT '服务日期 + 服务时间',

	status SMALLINT COMMENT '业务状态',

	errorCode VARCHAR(12) COMMENT '错误代码',

	errorMsg VARCHAR(300) COMMENT '错误信息,错误代码非0时，返回具体的错误原因',
	
	/*--- 以下为Response 报文体 部分  ---*/

	orderStatus VARCHAR(20) COMMENT '订单处理状态',

    extFiled1 VARCHAR(200) COMMENT '备用字段1',

    extFiled2 VARCHAR(200) COMMENT '备用字段2',

    extFiled3 VARCHAR(200) COMMENT '备用字段3',
    
    INDEX idx_ghbank_ogw00045_acNo (acNo)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 6.6	单笔充值结果查询 (OGW00046)
 */
CREATE TABLE ghbank_ogw00046(

	priority SMALLINT NOT NULL DEFAULT 1 COMMENT '优先级 1：普通 2：紧急 3：特急',

	invokeMethod SMALLINT NOT NULL DEFAULT 1 COMMENT '调用方式 1：同步 2：异步（需主动回查）',

	channelFlow CHAR(28) NOT NULL PRIMARY KEY COMMENT '同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）',

	channelDateTime DATETIME NOT NULL COMMENT '渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期',

	transCode CHAR(8) NOT NULL COMMENT '交易码',

	appId CHAR(3) NOT NULL DEFAULT 'PC' COMMENT '应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX',

	oldReqSeqNo VARCHAR(28) NOT NULL COMMENT '原充值交易流水号',

	serverFlow CHAR(20) COMMENT '服务流水号',

	serverDateTime DATETIME COMMENT '服务日期 + 服务时间',

	status SMALLINT COMMENT '业务状态',

	errorCode VARCHAR(12) COMMENT '错误代码',

	errorMsg VARCHAR(300) COMMENT '错误信息,错误代码非0时，返回具体的错误原因',

	returnStatus CHAR(1) COMMENT '交易状态',

	returnErrorMsg VARCHAR(32) COMMENT '失败原因',

	resJnlNo VARCHAR(32) COMMENT '银行交易流水号',

	transDateTime DATETIME COMMENT '交易日期 + 交易时间',

	extFiled1 VARCHAR(200) COMMENT '备用字段1',

	extFiled2 VARCHAR(200) COMMENT '备用字段2',

	extFiled3 VARCHAR(200) COMMENT '备用字段3'

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 6.7	单笔提现(OGW00047)
 */
CREATE TABLE ghbank_ogw00047(

    /*--- 以下为Request 报文头 部分  ---*/

    priority SMALLINT NOT NULL DEFAULT 1 COMMENT '优先级 1：普通 2：紧急 3：特急',

    invokeMethod SMALLINT NOT NULL DEFAULT 1 COMMENT '调用方式 1：同步 2：异步（需主动回查）',

    channelFlow CHAR(28) NOT NULL PRIMARY KEY COMMENT '同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）',

    channelDateTime DATETIME NOT NULL COMMENT '渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期',

	transCode CHAR(8) NOT NULL COMMENT '交易码',
	
	/*--- 以下为Request 报文体 部分  ---*/

    appId CHAR(3) NOT NULL DEFAULT 'PC' COMMENT '应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX',

    tTrans SMALLINT NOT NULL DEFAULT 0 COMMENT '交易类型',

    acNo VARCHAR(32) NOT NULL COMMENT '银行账号',

    acName VARCHAR(128) NOT NULL COMMENT '账号户名',

    amount DECIMAL(15,2) NOT NULL COMMENT '交易金额',

	remark VARCHAR(128) COMMENT '备注',
	
	/*--- 以下为Response 报文头 部分  ---*/

	serverFlow CHAR(20) COMMENT '服务流水号',

	serverDateTime DATETIME COMMENT '服务日期 + 服务时间',

	status SMALLINT COMMENT '业务状态',

	errorCode VARCHAR(12) COMMENT '错误代码',

	errorMsg VARCHAR(300) COMMENT '错误信息,错误代码非0时，返回具体的错误原因',
	
	/*--- 以下为Response 报文体 部分  ---*/

	withdrawStatus SMALLINT UNSIGNED NULL COMMENT '订单处理状态。具体定义见com.yuanheng100.settlement.ghbank.consts.WithdrawStatus',

    extFiled1 VARCHAR(200) COMMENT '备用字段1',

    extFiled2 VARCHAR(200) COMMENT '备用字段2',

    extFiled3 VARCHAR(200) COMMENT '备用字段3',
    
    INDEX idx_ghbank_ogw00047_acNo (acNo)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 6.8	单笔提现结果查询(OGW00048)
 */
CREATE TABLE ghbank_ogw00048(

	priority SMALLINT NOT NULL DEFAULT 1 COMMENT '优先级 1：普通 2：紧急 3：特急',

	invokeMethod SMALLINT NOT NULL DEFAULT 1 COMMENT '调用方式 1：同步 2：异步（需主动回查）',

	channelFlow CHAR(28) NOT NULL PRIMARY KEY COMMENT '同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）',

	channelDateTime DATETIME NOT NULL COMMENT '渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期',

	transCode CHAR(8) NOT NULL COMMENT '交易码',

	appId CHAR(3) NOT NULL DEFAULT 'PC' COMMENT '应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX',

	transDt DATE NOT NULL COMMENT '原提现交易日期',

	oldReqSeqNo VARCHAR(28) NOT NULL COMMENT '原提现交易流水号',

	tTrans SMALLINT NOT NULL DEFAULT 0 COMMENT '交易类型',

	serverFlow CHAR(20) COMMENT '服务流水号',

	serverDateTime DATETIME COMMENT '服务日期 + 服务时间',

	status SMALLINT COMMENT '业务状态',

	errorCode VARCHAR(12) COMMENT '错误代码',

	errorMsg VARCHAR(300) COMMENT '错误信息,错误代码非0时，返回具体的错误原因',

	returnStatus CHAR(1) COMMENT '交易状态',

	returnErrorMsg VARCHAR(32) COMMENT '失败原因',

	resJnlNo VARCHAR(32) COMMENT '银行交易流水号',

	transDateTime DATETIME COMMENT '交易日期 + 交易时间',

	extFiled1 VARCHAR(200) COMMENT '备用字段1',

	extFiled2 VARCHAR(200) COMMENT '备用字段2',

	extFiled3 VARCHAR(200) COMMENT '备用字段3'

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 6.9	账户余额查询(OGW00049)
 */
CREATE TABLE ghbank_ogw00049(

    /*--- 以下为Request 报文头 部分  ---*/

	priority SMALLINT NOT NULL DEFAULT 1 COMMENT '优先级 1：普通 2：紧急 3：特急',

	invokeMethod SMALLINT NOT NULL DEFAULT 1 COMMENT '调用方式 1：同步 2：异步（需主动回查）',

	channelFlow CHAR(28) NOT NULL PRIMARY KEY COMMENT '同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）',

	channelDateTime DATETIME NOT NULL COMMENT '渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期',

	transCode CHAR(8) NOT NULL COMMENT '交易码',
	
	/*--- 以下为Request 报文体部分  ---*/

	appId CHAR(3) NOT NULL DEFAULT 'PC' COMMENT '应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX',

	busType VARCHAR(5) COMMENT '业务类型',

	acNo VARCHAR(32) NOT NULL COMMENT '银行账号',

	acName VARCHAR(64) NOT NULL COMMENT '银行户名',

	serverFlow CHAR(20) COMMENT '服务流水号',

	serverDateTime DATETIME COMMENT '服务日期 + 服务时间',

	status SMALLINT COMMENT '业务状态',

	errorCode VARCHAR(12) COMMENT '错误代码',

	errorMsg VARCHAR(300) COMMENT '错误信息,错误代码非0时，返回具体的错误原因',

	acctBal DECIMAL(15,2) NOT NULL COMMENT '账户余额',

	availableBal DECIMAL(15,2) NOT NULL COMMENT '可用余额',

	frozBl DECIMAL(15,2) NOT NULL COMMENT '冻结金额',

	extFiled1 VARCHAR(200) COMMENT 'E账户状态',

	extFiled2 VARCHAR(200) COMMENT '备用字段2',

	extFiled3 VARCHAR(200) COMMENT '备用字段3'

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 6.11	单笔发标信息通知(OGW00051)
 */
CREATE TABLE ghbank_ogw00051(

    /*--- 以下为Request 报文头 部分  ---*/

    priority SMALLINT NOT NULL DEFAULT 1 COMMENT '优先级 1：普通 2：紧急 3：特急',

    invokeMethod SMALLINT NOT NULL DEFAULT 1 COMMENT '调用方式 1：同步 2：异步（需主动回查）',

    channelFlow CHAR(28) NOT NULL PRIMARY KEY COMMENT '同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）',

    channelDateTime DATETIME NOT NULL COMMENT '渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期',

	transCode CHAR(8) NOT NULL COMMENT '交易码',
	
	/*--- 以下为Request 报文体部分  ---*/

    appId CHAR(3) NOT NULL DEFAULT 'PC' COMMENT '应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX',

    loanNo BIGINT UNSIGNED NOT NULL COMMENT '借款编号（元亨小贷系统中的进件编号loanApplyId）。报文中是字符串，这里改成数字型',

    investId INT UNSIGNED NOT NULL COMMENT '标的编号，目前和借款编号一致',

    investObjName VARCHAR(128) NOT NULL COMMENT '标的名称',

    investObjInfo VARCHAR(1028) COMMENT '标的简介',

    minInvestAmt INT UNSIGNED COMMENT '最低投标金额。单位：元',

    maxInvestAmt INT UNSIGNED COMMENT '最高投标金额。单位：元',

    investObjAmt DECIMAL(15,2) NOT NULL COMMENT '总标的金额',

    investBeginDate DATE NOT NULL COMMENT '招标开始日期',

    investEndDate DATE NOT NULL COMMENT '招标到期日期',

    repayDate DATE COMMENT '还款日期',

    yearRate DECIMAL NOT NULL COMMENT '年利率',

    investRange INT UNSIGNED NOT NULL COMMENT '期限 整型，天数，单位为天',

    ratesType VARCHAR(128) COMMENT '计息方式',

    repaysType VARCHAR(128) COMMENT '还款方式',

    investObjState SMALLINT NOT NULL DEFAULT 0 COMMENT '标的状态 0 正常 1 撤销',

    bwTotalNum INT UNSIGNED NOT NULL COMMENT '借款人总数',

    zrFlag BOOLEAN DEFAULT FALSE COMMENT '是否为债券转让标的',

    refLoanNo BIGINT UNSIGNED COMMENT '债券转让原标的编号',

    oldReqSeq VARCHAR(28) COMMENT '原投标第三方交易流水号',

    bwAcName VARCHAR(128) NOT NULL COMMENT '借款人姓名',

    bwIdType INT UNSIGNED DEFAULT 1010 COMMENT '借款人证件类型,身份证：1010',

    bwIdNo VARCHAR(32) COMMENT '借款人证件号码',

    bwAcNo VARCHAR(32) NOT NULL COMMENT '借款人账号',

    bwAcBankId VARCHAR(64) COMMENT '借款人账号所属行号',

    bwAcBankName VARCHAR(256) COMMENT '借款人账号所属行名',

    bwAmt DECIMAL NOT NULL COMMENT '借款人金额',

    mortgageId VARCHAR(128) COMMENT '借款人抵押品编号',

    mortgageInfo VARCHAR(1024) COMMENT '借款人抵押品简单描述',

    checkDate DATE COMMENT '借款人审批通过日期',

	remark VARCHAR(128) COMMENT '备注',
	
	/*--- 以下为Response 报文头 部分  ---*/

	serverFlow CHAR(20) COMMENT '服务流水号',

	serverDateTime DATETIME COMMENT '服务日期 + 服务时间',

	status SMALLINT COMMENT '业务状态',

	errorCode VARCHAR(12) COMMENT '错误代码',

	errorMsg VARCHAR(300) COMMENT '错误信息,错误代码非0时，返回具体的错误原因',
	
	/*--- 以下为Response 报文体 部分  ---*/

	resJnlNo VARCHAR(32) COMMENT '银行交易流水号',

	transDateTime DATETIME COMMENT '交易日期 + 交易时间',

    extFiled1 VARCHAR(200) COMMENT '备用字段1',

    extFiled2 VARCHAR(200) COMMENT '备用字段2',

    extFiled3 VARCHAR(200) COMMENT '备用字段3',
    
    INDEX idx_ghbank_ogw00051_loanNo (loanNo),
    
    INDEX idx_ghbank_ogw00051_bwIdNo (bwIdNo),
    
    INDEX idx_ghbank_ogw00051_bwAcNo (bwAcNo)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 6.12	单笔投标 (OGW00052)
 */
CREATE TABLE ghbank_ogw00052(

	priority SMALLINT NOT NULL DEFAULT 1 COMMENT '优先级 1：普通 2：紧急 3：特急',

	invokeMethod SMALLINT NOT NULL DEFAULT 1 COMMENT '调用方式 1：同步 2：异步（需主动回查）',

	channelFlow CHAR(28) NOT NULL PRIMARY KEY COMMENT '同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）',

	channelDateTime DATETIME NOT NULL COMMENT '渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期',

	transCode CHAR(8) NOT NULL COMMENT '交易码',

	appId CHAR(3) NOT NULL DEFAULT 'PC' COMMENT '应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX',

	tTrans SMALLINT NOT NULL DEFAULT 0 COMMENT '交易类型',

	loanNo BIGINT UNSIGNED NOT NULL COMMENT '借款编号',

	acNo VARCHAR(32) NOT NULL COMMENT '投资人华兴银行e账户账号',

	acName VARCHAR(128) NOT NULL COMMENT '投资人账号户名',

	amount DECIMAL(15,2) NOT NULL COMMENT '投标金额',

	remark VARCHAR(60) COMMENT '备注',

	serverFlow CHAR(20) COMMENT '服务流水号',

	serverDateTime DATETIME COMMENT '服务日期 + 服务时间',

	status SMALLINT COMMENT '业务状态',

	errorCode VARCHAR(12) COMMENT '错误代码',

	errorMsg VARCHAR(300) COMMENT '错误信息,错误代码非0时，返回具体的错误原因',

	extFiled1 VARCHAR(200) COMMENT '备用字段1',

	extFiled2 VARCHAR(200) COMMENT '备用字段2',

	extFiled3 VARCHAR(200) COMMENT '备用字段3',
	
	INDEX idx_ghbank_ogw00052_loanNo (loanNo),
	
	INDEX idx_ghbank_ogw00052_acNo (acNo)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/**
 * 6.13	单笔投标结果查询(OGW00053)
 */
CREATE TABLE ghbank_ogw00053(

	priority SMALLINT NOT NULL DEFAULT 1 COMMENT '优先级 1：普通 2：紧急 3：特急',

	invokeMethod SMALLINT NOT NULL DEFAULT 1 COMMENT '调用方式 1：同步 2：异步（需主动回查）',

	channelFlow CHAR(28) NOT NULL PRIMARY KEY COMMENT '同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）',

	channelDateTime DATETIME NOT NULL COMMENT '渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期',

	transCode CHAR(8) NOT NULL COMMENT '交易码',

	appId CHAR(3) NOT NULL DEFAULT 'PC' COMMENT '应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX',

	oldReqSeqNo VARCHAR(28) NOT NULL COMMENT '原提现交易流水号',

	serverFlow CHAR(20) COMMENT '服务流水号',

	serverDateTime DATETIME COMMENT '服务日期 + 服务时间',

	status SMALLINT COMMENT '业务状态',

	errorCode VARCHAR(12) COMMENT '错误代码',

	errorMsg VARCHAR(300) COMMENT '错误信息,错误代码非0时，返回具体的错误原因',

	returnStatus CHAR(1) COMMENT '交易状态',

	returnErrorMsg VARCHAR(32) COMMENT '失败原因',

	resJnlNo VARCHAR(32) COMMENT '银行交易流水号',

	transDateTime DATETIME COMMENT '交易日期 + 交易时间',

	extFiled1 VARCHAR(200) COMMENT '备用字段1',

	extFiled2 VARCHAR(200) COMMENT '备用字段2',

	extFiled3 VARCHAR(200) COMMENT '备用字段3'

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 6.14	投标优惠返回（可选）(OGW00054)
 */
CREATE TABLE ghbank_ogw00054(

	priority SMALLINT NOT NULL DEFAULT 1 COMMENT '优先级 1：普通 2：紧急 3：特急',

	invokeMethod SMALLINT NOT NULL DEFAULT 1 COMMENT '调用方式 1：同步 2：异步（需主动回查）',

	channelFlow CHAR(28) NOT NULL PRIMARY KEY COMMENT '同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）',

	channelDateTime DATETIME NOT NULL COMMENT '渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期',

	transCode CHAR(8) NOT NULL COMMENT '交易码',

	appId CHAR(3) NOT NULL DEFAULT 'PC' COMMENT '应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX',

	loanNo BIGINT UNSIGNED NOT NULL COMMENT '借款编号',

	bwAcName VARCHAR(128) NOT NULL COMMENT '还款账号户名',

	bwAcNo VARCHAR(32) NOT NULL COMMENT '还款人华兴银行e账户账号',

	amount DECIMAL(15,2) NOT NULL COMMENT '优惠总金额',

	totalNum INT UNSIGNED NOT NULL COMMENT '优惠总笔数',

	serverFlow CHAR(20) COMMENT '服务流水号',

	serverDateTime DATETIME COMMENT '服务日期 + 服务时间',

	status SMALLINT COMMENT '业务状态',

	errorCode VARCHAR(12) COMMENT '错误代码',

	errorMsg VARCHAR(300) COMMENT '错误信息,错误代码非0时，返回具体的错误原因',

	extFiled1 VARCHAR(200) COMMENT '备用字段1',

	extFiled2 VARCHAR(200) COMMENT '备用字段2',

	extFiled3 VARCHAR(200) COMMENT '备用字段3'

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 返还优惠具体项
 */
CREATE TABLE ghbank_ogw00054_feedback(

	subSeqNo VARCHAR(32) NOT NULL COMMENT '子流水号',

	oldReqSeqNo VARCHAR(28) NOT NULL COMMENT '原投标流水号',

	acNo VARCHAR(32) NOT NULL COMMENT '投资人华兴银行e账户账号',

	acName VARCHAR(128) NOT NULL COMMENT '投资人账号户名',

	amount DECIMAL(15,2) NOT NULL COMMENT '优惠金额',

	remark VARCHAR(128) COMMENT '备注',

	extFiled3 VARCHAR(200) COMMENT '备用字段3',

	status CHAR(1) COMMENT '状态 L 待处理 R 正在处理 N 未明 F失败 S成功',

	errorMsg VARCHAR(128) COMMENT '错误原因'

) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/**
 * 6.15	投标优惠返回结果查询
 */
CREATE TABLE ghbank_ogw00055(

	priority SMALLINT NOT NULL DEFAULT 1 COMMENT '优先级 1：普通 2：紧急 3：特急',

	invokeMethod SMALLINT NOT NULL DEFAULT 1 COMMENT '调用方式 1：同步 2：异步（需主动回查）',

	channelFlow CHAR(28) NOT NULL PRIMARY KEY COMMENT '同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）',

	channelDateTime DATETIME NOT NULL COMMENT '渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期',

	transCode CHAR(8) NOT NULL COMMENT '交易码',

	appId CHAR(3) NOT NULL DEFAULT 'PC' COMMENT '应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX',

	oldReqSeqNo VARCHAR(28) NOT NULL COMMENT '原投标优惠返回交易流水号',

	subSeqNo VARCHAR(32) NOT NULL COMMENT '子流水号',

	serverFlow CHAR(20) COMMENT '服务流水号',

	serverDateTime DATETIME COMMENT '服务日期 + 服务时间',

	status SMALLINT COMMENT '业务状态',

	errorCode VARCHAR(12) COMMENT '错误代码',

	errorMsg VARCHAR(300) COMMENT '错误信息,错误代码非0时，返回具体的错误原因',

	resJnlNo VARCHAR(32) COMMENT '银行交易流水号',

	transDateTime DATETIME COMMENT '交易日期 + 交易时间',

	loanNo BIGINT UNSIGNED NOT NULL COMMENT '借款编号',

	bwAcName VARCHAR(128) NOT NULL COMMENT '还款账号户名',

	bwAcNo VARCHAR(32) NOT NULL COMMENT '还款账号',

	amount DECIMAL(15,2) NOT NULL COMMENT '优惠总金额',

	totalNum INT UNSIGNED NOT NULL COMMENT '优惠总笔数',

	returnStatus CHAR(1) COMMENT '交易状态',

	returnErrorMsg VARCHAR(32) COMMENT '失败原因',

	extFiled1 VARCHAR(200) COMMENT '备用字段1',

	extFiled2 VARCHAR(200) COMMENT '备用字段2',

	extFiled3 VARCHAR(200) COMMENT '备用字段3'

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 6.20	单笔撤标(OGW00060)
 */
CREATE TABLE ghbank_ogw00060(

	priority SMALLINT NOT NULL DEFAULT 1 COMMENT '优先级 1：普通 2：紧急 3：特急',

	invokeMethod SMALLINT NOT NULL DEFAULT 1 COMMENT '调用方式 1：同步 2：异步（需主动回查）',

	channelFlow CHAR(28) NOT NULL PRIMARY KEY COMMENT '同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）',

	channelDateTime DATETIME NOT NULL COMMENT '渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期',

	transCode CHAR(8) NOT NULL COMMENT '交易码',

	appId CHAR(3) NOT NULL DEFAULT 'PC' COMMENT '应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX',

	loanNo BIGINT UNSIGNED NOT NULL COMMENT '借款编号',

	oldReqSeqNo VARCHAR(28) NOT NULL COMMENT '原投标流水号',

	acNo VARCHAR(32) NOT NULL COMMENT '投资人华兴银行e账户账号',

	acName VARCHAR(128) NOT NULL COMMENT '投资人账号户名',

	cancelReason VARCHAR(128) COMMENT '撤标原因',

	serverFlow CHAR(20) COMMENT '服务流水号',

	serverDateTime DATETIME COMMENT '服务日期 + 服务时间',

	status SMALLINT COMMENT '业务状态',

	errorCode VARCHAR(12) COMMENT '错误代码',

	errorMsg VARCHAR(300) COMMENT '错误信息,错误代码非0时，返回具体的错误原因',

	resJnlNo VARCHAR(32) COMMENT '银行交易流水号',

	transDateTime DATETIME COMMENT '交易日期 + 交易时间',

	extFiled1 VARCHAR(200) COMMENT '备用字段1',

	extFiled2 VARCHAR(200) COMMENT '备用字段2',

	extFiled3 VARCHAR(200) COMMENT '备用字段3'

) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/**
 * 6.21	银行主动单笔撤标（必须）(OGW0014T)
 */
CREATE TABLE ghbank_ogw0014t(

	transCode CHAR(8) NOT NULL COMMENT '交易码',

	serverFlow CHAR(20) COMMENT '服务流水号',

	serverDateTime DATETIME COMMENT '服务日期 + 服务时间',

	status SMALLINT COMMENT '业务状态',

	errorCode VARCHAR(12) COMMENT '错误代码',

	errorMsg VARCHAR(300) COMMENT '错误信息,错误代码非0时，返回具体的错误原因',

	loanNo BIGINT UNSIGNED NOT NULL COMMENT '借款编号',

	oldReqSeqNo VARCHAR(28) NOT NULL COMMENT '原投标流水号',

	acNo VARCHAR(32) NOT NULL COMMENT '投资人华兴银行e账户账号',

	acName VARCHAR(128) NOT NULL COMMENT '投资人账号户名',

	cancelReason VARCHAR(128) COMMENT '撤标原因',

	extFiled1 VARCHAR(200) COMMENT '备用字段1',

	extFiled2 VARCHAR(200) COMMENT '备用字段2',

	extFiled3 VARCHAR(200) COMMENT '备用字段3'

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 6.22	债券转让申请(OGW00061)
 */
CREATE TABLE ghbank_ogw00061(

	priority SMALLINT NOT NULL DEFAULT 1 COMMENT '优先级 1：普通 2：紧急 3：特急',

	invokeMethod SMALLINT NOT NULL DEFAULT 1 COMMENT '调用方式 1：同步 2：异步（需主动回查）',

	channelFlow CHAR(28) NOT NULL PRIMARY KEY COMMENT '同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）',

	channelDateTime DATETIME NOT NULL COMMENT '渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期',

	transCode CHAR(8) NOT NULL COMMENT '交易码',

	appId CHAR(3) NOT NULL DEFAULT 'PC' COMMENT '应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX',

	tTrans SMALLINT NOT NULL DEFAULT 0 COMMENT '交易类型',

	oldReqSeqNo VARCHAR(28) NOT NULL COMMENT '原投标流水号',

	oldReqNumber INT UNSIGNED NOT NULL COMMENT '原标的编号',

	oldReqName VARCHAR(512) NOT NULL COMMENT '原标的名称',

	accNo VARCHAR(32) NOT NULL COMMENT '转让人华兴银行e账户账号',

	custName VARCHAR(128) NOT NULL COMMENT '转让人名称',

	amount DECIMAL(15,2) NOT NULL COMMENT '剩余金额',

	preIncome DECIMAL(15,2) NOT NULL COMMENT '预计剩余收益',

	remark VARCHAR(60) COMMENT '备注',

	returnUrl VARCHAR(128) COMMENT '返回商户URL 不提供此地址，则客户在我行页面处理完后无法跳转到商户指定页面。',

	serverFlow CHAR(20) COMMENT '服务流水号',

	serverDateTime DATETIME COMMENT '服务日期 + 服务时间',

	status SMALLINT COMMENT '业务状态',

	errorCode VARCHAR(12) COMMENT '错误代码',

	errorMsg VARCHAR(300) COMMENT '错误信息,错误代码非0时，返回具体的错误原因',

	extFiled1 VARCHAR(200) COMMENT '备用字段1',

	extFiled2 VARCHAR(200) COMMENT '备用字段2',

	extFiled3 VARCHAR(200) COMMENT '备用字段3',
	
	INDEX idx_ghbank_ogw00061_oldReqSeqNo (oldReqSeqNo),
	
	INDEX idx_ghbank_ogw00061_oldReqNumber (oldReqNumber),
	
	INDEX idx_ghbank_ogw00061_accNo (accNo)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 6.23	债券转让结果查询(OGW00062)
 */
CREATE TABLE ghbank_ogw00062(

	priority SMALLINT NOT NULL DEFAULT 1 COMMENT '优先级 1：普通 2：紧急 3：特急',

	invokeMethod SMALLINT NOT NULL DEFAULT 1 COMMENT '调用方式 1：同步 2：异步（需主动回查）',

	channelFlow CHAR(28) NOT NULL PRIMARY KEY COMMENT '同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）',

	channelDateTime DATETIME NOT NULL COMMENT '渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期',

	transCode CHAR(8) NOT NULL COMMENT '交易码',

	appId CHAR(3) NOT NULL DEFAULT 'PC' COMMENT '应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX',

	oldReqSeqNo VARCHAR(28) NOT NULL COMMENT '原债券转让申请流水',

	serverFlow CHAR(20) COMMENT '服务流水号',

	serverDateTime DATETIME COMMENT '服务日期 + 服务时间',

	status SMALLINT COMMENT '业务状态',

	errorCode VARCHAR(12) COMMENT '错误代码',

	errorMsg VARCHAR(300) COMMENT '错误信息,错误代码非0时，返回具体的错误原因',

	resJnlNo VARCHAR(32) COMMENT '银行交易流水号',

	transDateTime DATETIME COMMENT '交易日期 + 交易时间',

	returnStatus CHAR(1) COMMENT '交易状态',

	returnErrorMsg VARCHAR(32) COMMENT '失败原因',

	extFiled1 VARCHAR(200) COMMENT '备用字段1',

	extFiled2 VARCHAR(200) COMMENT '备用字段2',

	extFiled3 VARCHAR(200) COMMENT '备用字段3',
	
	INDEX idx_ghbank_ogw00062_oldReqSeqNo (oldReqSeqNo)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 6.24	流标(OGW00063)
 */
CREATE TABLE ghbank_ogw00063(

	priority SMALLINT NOT NULL DEFAULT 1 COMMENT '优先级 1：普通 2：紧急 3：特急',

	invokeMethod SMALLINT NOT NULL DEFAULT 1 COMMENT '调用方式 1：同步 2：异步（需主动回查）',

	channelFlow CHAR(28) NOT NULL PRIMARY KEY COMMENT '同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）',

	channelDateTime DATETIME NOT NULL COMMENT '渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期',

	transCode CHAR(8) NOT NULL COMMENT '交易码',

	appId CHAR(3) NOT NULL DEFAULT 'PC' COMMENT '应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX',

	loanNo BIGINT UNSIGNED NOT NULL COMMENT '借款编号',

	cancelReason VARCHAR(128) COMMENT '流标原因',

	serverFlow CHAR(20) COMMENT '服务流水号',

	serverDateTime DATETIME COMMENT '服务日期 + 服务时间',

	status SMALLINT COMMENT '业务状态',

	errorCode VARCHAR(12) COMMENT '错误代码',

	errorMsg VARCHAR(300) COMMENT '错误信息,错误代码非0时，返回具体的错误原因',

	resJnlNo VARCHAR(32) COMMENT '银行交易流水号',

	transDateTime DATETIME COMMENT '交易日期 + 交易时间',

	extFiled1 VARCHAR(200) COMMENT '备用字段1',

	extFiled2 VARCHAR(200) COMMENT '备用字段2',

	extFiled3 VARCHAR(200) COMMENT '备用字段3'

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 6.25	银行主动流标（必须）(OGW0015T)
 */
CREATE TABLE ghbank_ogw0015t(

	transCode CHAR(8) NOT NULL COMMENT '交易码',

	serverFlow CHAR(20) COMMENT '服务流水号',

	serverDateTime DATETIME COMMENT '服务日期 + 服务时间',

	status SMALLINT COMMENT '业务状态',

	errorCode VARCHAR(12) COMMENT '错误代码',

	errorMsg VARCHAR(300) COMMENT '错误信息,错误代码非0时，返回具体的错误原因',

	loanNo BIGINT UNSIGNED NOT NULL COMMENT '借款编号',

	cancelReason VARCHAR(128) COMMENT '流标原因',

	extFiled1 VARCHAR(200) COMMENT '备用字段1',

	extFiled2 VARCHAR(200) COMMENT '备用字段2',

	extFiled3 VARCHAR(200) COMMENT '备用字段3'

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 6.26	流标结果查询 (OGW00064)
 */
CREATE TABLE ghbank_ogw00064(

	priority SMALLINT NOT NULL DEFAULT 1 COMMENT '优先级 1：普通 2：紧急 3：特急',

	invokeMethod SMALLINT NOT NULL DEFAULT 1 COMMENT '调用方式 1：同步 2：异步（需主动回查）',

	channelFlow CHAR(28) NOT NULL PRIMARY KEY COMMENT '同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）',

	channelDateTime DATETIME NOT NULL COMMENT '渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期',

	transCode CHAR(8) NOT NULL COMMENT '交易码',

	appId CHAR(3) NOT NULL DEFAULT 'PC' COMMENT '应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX',

	oldReqSeqNo VARCHAR(28) NOT NULL COMMENT '原流标交易流水号',

	oldTTJnl VARCHAR(28) COMMENT '原投标流水号',

	serverFlow CHAR(20) COMMENT '服务流水号',

	serverDateTime DATETIME COMMENT '服务日期 + 服务时间',

	status SMALLINT COMMENT '业务状态',

	errorCode VARCHAR(12) COMMENT '错误代码',

	errorMsg VARCHAR(300) COMMENT '错误信息,错误代码非0时，返回具体的错误原因',

	returnStatus CHAR(1) COMMENT '交易状态',

	returnErrorMsg VARCHAR(32) COMMENT '失败原因',

	extFiled1 VARCHAR(200) COMMENT '备用字段1',

	extFiled2 VARCHAR(200) COMMENT '备用字段2',

	extFiled3 VARCHAR(200) COMMENT '备用字段3'

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 6.27	放款(OGW00065)
 */
CREATE TABLE ghbank_ogw00065(

	priority SMALLINT NOT NULL DEFAULT 1 COMMENT '优先级 1：普通 2：紧急 3：特急',

	invokeMethod SMALLINT NOT NULL DEFAULT 1 COMMENT '调用方式 1：同步 2：异步（需主动回查）',

	channelFlow CHAR(28) NOT NULL PRIMARY KEY COMMENT '同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）',

	channelDateTime DATETIME NOT NULL COMMENT '渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期',

	transCode CHAR(8) NOT NULL COMMENT '交易码',

	appId CHAR(3) NOT NULL DEFAULT 'PC' COMMENT '应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX',

	loanNo BIGINT UNSIGNED NOT NULL COMMENT '借款编号',

	bwAcName VARCHAR(128) NOT NULL COMMENT '借款人姓名',

	bwAcNo VARCHAR(32) NOT NULL COMMENT '借款人华兴银行e账户账号',

	acMngAmt DECIMAL(15,2) COMMENT '账户管理费',

	guarantAmt DECIMAL(15,2) COMMENT '风险保证金',

	remark VARCHAR(60) COMMENT '备注',

	serverFlow CHAR(20) COMMENT '服务流水号',

	serverDateTime DATETIME COMMENT '服务日期 + 服务时间',

	status SMALLINT COMMENT '业务状态',

	errorCode VARCHAR(12) COMMENT '错误代码',

	errorMsg VARCHAR(300) COMMENT '错误信息,错误代码非0时，返回具体的错误原因',

	resJnlNo VARCHAR(32) COMMENT '银行交易流水号',

	transDateTime DATETIME COMMENT '交易日期 + 交易时间',

	extFiled1 VARCHAR(200) COMMENT '备用字段1',

	extFiled2 VARCHAR(200) COMMENT '备用字段2',

	extFiled3 VARCHAR(200) COMMENT '备用字段3',
	
	INDEX idx_ghbank_ogw00065_loanNo (loanNo)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 6.28	放款结果查询 (OGW00066)
 */
CREATE TABLE ghbank_ogw00066(

	priority SMALLINT NOT NULL DEFAULT 1 COMMENT '优先级 1：普通 2：紧急 3：特急',

	invokeMethod SMALLINT NOT NULL DEFAULT 1 COMMENT '调用方式 1：同步 2：异步（需主动回查）',

	channelFlow CHAR(28) NOT NULL PRIMARY KEY COMMENT '同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）',

	channelDateTime DATETIME NOT NULL COMMENT '渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期',

	transCode CHAR(8) NOT NULL COMMENT '交易码',

	appId CHAR(3) NOT NULL DEFAULT 'PC' COMMENT '应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX',

	oldReqSeqNo VARCHAR(28) NOT NULL COMMENT '原放款交易流水号',

	loanNo BIGINT UNSIGNED NOT NULL COMMENT '借款编号',

	oldTTJnl VARCHAR(28) COMMENT '原投标流水号',

	serverFlow CHAR(20) COMMENT '服务流水号',

	serverDateTime DATETIME COMMENT '服务日期 + 服务时间',

	status SMALLINT COMMENT '业务状态',

	errorCode VARCHAR(12) COMMENT '错误代码',

	errorMsg VARCHAR(300) COMMENT '错误信息,错误代码非0时，返回具体的错误原因',

	bwAcName VARCHAR(128) NOT NULL COMMENT '借款人姓名',

	bwAcNo VARCHAR(32) NOT NULL COMMENT '借款人华兴银行e账户账号',

	acMngAmt DECIMAL(15,2) COMMENT '账户管理费',

	guarantAmt DECIMAL(15,2) COMMENT '风险保证金',

	extFiled1 VARCHAR(200) COMMENT '备用字段1',

	extFiled2 VARCHAR(200) COMMENT '备用字段2',

	extFiled3 VARCHAR(200) COMMENT '备用字段3',
	
	INDEX idx_ghbank_ogw00066_oldReqSeqNo (oldReqSeqNo),
	
	INDEX idx_ghbank_ogw00066_loanNo (loanNo)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 6.29	借款人单标还款 (OGW00067)
 */
CREATE TABLE ghbank_ogw00067(

    /*--- 以下为Request 报文头 部分  ---*/

	priority SMALLINT NOT NULL DEFAULT 1 COMMENT '优先级 1：普通 2：紧急 3：特急',

	invokeMethod SMALLINT NOT NULL DEFAULT 1 COMMENT '调用方式 1：同步 2：异步（需主动回查）',

	channelFlow CHAR(28) NOT NULL PRIMARY KEY COMMENT '同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）',

	channelDateTime DATETIME NOT NULL COMMENT '渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期',

	transCode CHAR(8) NOT NULL COMMENT '交易码',
	
	/*--- 以下为Request 报文体 部分  ---*/

	appId CHAR(3) NOT NULL DEFAULT 'PC' COMMENT '应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX',

	tTrans SMALLINT NOT NULL DEFAULT 0 COMMENT '交易类型',

	dfFlag SMALLINT NOT NULL COMMENT '还款类型 1=正常还款 2=垫付后，借款人还款',

	oldReqSeqNo VARCHAR(28) NULL COMMENT '原垫付请求流水号 垫付还款时必需',

	loanNo BIGINT UNSIGNED NOT NULL COMMENT '借款编号',

	bwAcName VARCHAR(128) NOT NULL COMMENT '借款人姓名',

	bwAcNo VARCHAR(32) NOT NULL COMMENT '借款人华兴银行e账户账号',

	amount DECIMAL(15,2) NOT NULL COMMENT '还款金额',

	remark VARCHAR(60) COMMENT '备注',

	returnUrl VARCHAR(128) COMMENT '返回商户URL 不提供此地址，则客户在我行页面处理完后无法跳转到商户指定页面。',
	
	/*--- 以下为Response 报文头 部分  ---*/

	serverFlow CHAR(20) COMMENT '服务流水号',

	serverDateTime DATETIME COMMENT '服务日期 + 服务时间',

	status SMALLINT COMMENT '业务状态',

	errorCode VARCHAR(12) COMMENT '错误代码',

	errorMsg VARCHAR(300) COMMENT '错误信息,错误代码非0时，返回具体的错误原因',

	extFiled1 VARCHAR(200) COMMENT '备用字段1',

	extFiled2 VARCHAR(200) COMMENT '备用字段2',

	extFiled3 VARCHAR(200) COMMENT '备用字段3'

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 6.30	借款人单标还款结果查询(OGW00068)
 */
CREATE TABLE ghbank_ogw00068(

	priority SMALLINT NOT NULL DEFAULT 1 COMMENT '优先级 1：普通 2：紧急 3：特急',

	invokeMethod SMALLINT NOT NULL DEFAULT 1 COMMENT '调用方式 1：同步 2：异步（需主动回查）',

	channelFlow CHAR(28) NOT NULL PRIMARY KEY COMMENT '同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）',

	channelDateTime DATETIME NOT NULL COMMENT '渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期',

	transCode CHAR(8) NOT NULL COMMENT '交易码',

	appId CHAR(3) NOT NULL DEFAULT 'PC' COMMENT '应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX',

	oldReqSeqNo VARCHAR(28) NOT NULL COMMENT '原借款人单标还款交易流水号',

	serverFlow CHAR(20) COMMENT '服务流水号',

	serverDateTime DATETIME COMMENT '服务日期 + 服务时间',

	status SMALLINT COMMENT '业务状态',

	errorCode VARCHAR(12) COMMENT '错误代码',

	errorMsg VARCHAR(300) COMMENT '错误信息,错误代码非0时，返回具体的错误原因',

	returnStatus CHAR(1) COMMENT '交易状态',

	returnErrorMsg VARCHAR(32) COMMENT '失败原因',

	resJnlNo VARCHAR(32) COMMENT '银行交易流水号',

	transDateTime DATETIME COMMENT '交易日期 + 交易时间',

	loanNo BIGINT UNSIGNED NOT NULL COMMENT '借款编号',

	bwAcName VARCHAR(128) NOT NULL COMMENT '还款账号户名',

	bwAcNo VARCHAR(32) NOT NULL COMMENT '还款人华兴银行e账户账号',

	amount DECIMAL(15,2) COMMENT '还款金额',

	extFiled1 VARCHAR(200) COMMENT '备用字段1',

	extFiled2 VARCHAR(200) COMMENT '备用字段2',

	extFiled3 VARCHAR(200) COMMENT '备用字段3'

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 6.31	自动还款授权 (OGW00069)
 */
CREATE TABLE ghbank_ogw00069(

	priority SMALLINT NOT NULL DEFAULT 1 COMMENT '优先级 1：普通 2：紧急 3：特急',

	invokeMethod SMALLINT NOT NULL DEFAULT 1 COMMENT '调用方式 1：同步 2：异步（需主动回查）',

	channelFlow CHAR(28) NOT NULL PRIMARY KEY COMMENT '同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）',

	channelDateTime DATETIME NOT NULL COMMENT '渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期',

	transCode CHAR(8) NOT NULL COMMENT '交易码',

	appId CHAR(3) NOT NULL DEFAULT 'PC' COMMENT '应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX',

	tTrans SMALLINT NOT NULL DEFAULT 0 COMMENT '交易类型',

	loanNo BIGINT UNSIGNED NOT NULL COMMENT '借款编号',

	bwAcName VARCHAR(128) NOT NULL COMMENT '还款账号户名',

	bwAcNo VARCHAR(32) NOT NULL COMMENT '还款人华兴银行e账户账号',

	remark VARCHAR(60) COMMENT '备注',

	returnUrl VARCHAR(128) COMMENT '返回商户URL 不提供此地址，则客户在我行页面处理完后无法跳转到商户指定页面。',

	serverFlow CHAR(20) COMMENT '服务流水号',

	serverDateTime DATETIME COMMENT '服务日期 + 服务时间',

	status SMALLINT COMMENT '业务状态',

	errorCode VARCHAR(12) COMMENT '错误代码',

	errorMsg VARCHAR(300) COMMENT '错误信息,错误代码非0时，返回具体的错误原因',

	resJnlNo VARCHAR(32) COMMENT '银行交易流水号',

	extFiled1 VARCHAR(200) COMMENT '备用字段1',

	extFiled2 VARCHAR(200) COMMENT '备用字段2',

	extFiled3 VARCHAR(200) COMMENT '备用字段3'

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 6.32	自动还款授权结果查询（可选）(OGW00070)
 */
CREATE TABLE ghbank_ogw00070(

	priority SMALLINT NOT NULL DEFAULT 1 COMMENT '优先级 1：普通 2：紧急 3：特急',

	invokeMethod SMALLINT NOT NULL DEFAULT 1 COMMENT '调用方式 1：同步 2：异步（需主动回查）',

	channelFlow CHAR(28) NOT NULL PRIMARY KEY COMMENT '同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）',

	channelDateTime DATETIME NOT NULL COMMENT '渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期',

	transCode CHAR(8) NOT NULL COMMENT '交易码',

	appId CHAR(3) NOT NULL DEFAULT 'PC' COMMENT '应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX',

	oldReqSeqNo VARCHAR(28) NOT NULL COMMENT '原自动还款授权交易流水号',

	serverFlow CHAR(20) COMMENT '服务流水号',

	serverDateTime DATETIME COMMENT '服务日期 + 服务时间',

	status SMALLINT COMMENT '业务状态',

	errorCode VARCHAR(12) COMMENT '错误代码',

	errorMsg VARCHAR(300) COMMENT '错误信息,错误代码非0时，返回具体的错误原因',

	returnStatus CHAR(1) COMMENT '交易状态',

	returnErrorMsg VARCHAR(32) COMMENT '失败原因',

	resJnlNo VARCHAR(32) COMMENT '银行交易流水号',

	transDateTime DATETIME COMMENT '交易日期 + 交易时间',

	extFiled1 VARCHAR(200) COMMENT '备用字段1',

	extFiled2 VARCHAR(200) COMMENT '备用字段2',

	extFiled3 VARCHAR(200) COMMENT '备用字段3'

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 6.33	自动还款授权撤销（可选）(OGW00071)
 */
CREATE TABLE ghbank_ogw00071(

	priority SMALLINT NOT NULL DEFAULT 1 COMMENT '优先级 1：普通 2：紧急 3：特急',

	invokeMethod SMALLINT NOT NULL DEFAULT 1 COMMENT '调用方式 1：同步 2：异步（需主动回查）',

	channelFlow CHAR(28) NOT NULL PRIMARY KEY COMMENT '同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）',

	channelDateTime DATETIME NOT NULL COMMENT '渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期',

	transCode CHAR(8) NOT NULL COMMENT '交易码',

	appId CHAR(3) NOT NULL DEFAULT 'PC' COMMENT '应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX',

	otpSeqNo VARCHAR(32) NOT NULL COMMENT '动态密码唯一标识',

	otpNo VARCHAR(10) NOT NULL COMMENT '动态密码',

	loanNo BIGINT UNSIGNED NOT NULL COMMENT '借款编号',

	bwAcName VARCHAR(128) NOT NULL COMMENT '还款账号户名',

	bwAcNo VARCHAR(32) NOT NULL COMMENT '还款人华兴银行e账户账号',

	remark VARCHAR(60) COMMENT '备注',

	serverFlow CHAR(20) COMMENT '服务流水号',

	serverDateTime DATETIME COMMENT '服务日期 + 服务时间',

	status SMALLINT COMMENT '业务状态',

	errorCode VARCHAR(12) COMMENT '错误代码',

	errorMsg VARCHAR(300) COMMENT '错误信息,错误代码非0时，返回具体的错误原因',

	resJnlNo VARCHAR(32) COMMENT '银行交易流水号',

	transDateTime DATETIME COMMENT '交易日期 + 交易时间',

	extFiled1 VARCHAR(200) COMMENT '备用字段1',

	extFiled2 VARCHAR(200) COMMENT '备用字段2',

	extFiled3 VARCHAR(200) COMMENT '备用字段3'

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 6.34	自动单笔还款（可选）(OGW00072)
 */
CREATE TABLE ghbank_ogw00072(

	priority SMALLINT NOT NULL DEFAULT 1 COMMENT '优先级 1：普通 2：紧急 3：特急',

	invokeMethod SMALLINT NOT NULL DEFAULT 1 COMMENT '调用方式 1：同步 2：异步（需主动回查）',

	channelFlow CHAR(28) NOT NULL PRIMARY KEY COMMENT '同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）',

	channelDateTime DATETIME NOT NULL COMMENT '渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期',

	transCode CHAR(8) NOT NULL COMMENT '交易码',

	appId CHAR(3) NOT NULL DEFAULT 'PC' COMMENT '应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX',

	loanNo BIGINT UNSIGNED NOT NULL COMMENT '借款编号',

	bwAcName VARCHAR(128) NOT NULL COMMENT '还款账号户名',

	bwAcNo VARCHAR(32) NOT NULL COMMENT '还款人华兴银行e账户账号',

	feeAmt DECIMAL(15,2) NOT NULL DEFAULT 0.00 COMMENT '手续费',

	amount DECIMAL(15,2) NOT NULL COMMENT '还款金额',

	remark VARCHAR(60) COMMENT '备注',

	serverFlow CHAR(20) COMMENT '服务流水号',

	serverDateTime DATETIME COMMENT '服务日期 + 服务时间',

	status SMALLINT COMMENT '业务状态',

	errorCode VARCHAR(12) COMMENT '错误代码',

	errorMsg VARCHAR(300) COMMENT '错误信息,错误代码非0时，返回具体的错误原因',

	resJnlNo VARCHAR(32) COMMENT '银行交易流水号',

	transDateTime DATETIME COMMENT '交易日期 + 交易时间',

	extFiled1 VARCHAR(200) COMMENT '备用字段1',

	extFiled2 VARCHAR(200) COMMENT '备用字段2',

	extFiled3 VARCHAR(200) COMMENT '备用字段3'

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 6.35	单标公司垫付还款(OGW00073)
 */
CREATE TABLE ghbank_ogw00073(

    /*--- 以下为Request 报文头 部分  ---*/

	priority SMALLINT NOT NULL DEFAULT 1 COMMENT '优先级 1：普通 2：紧急 3：特急',

	invokeMethod SMALLINT NOT NULL DEFAULT 1 COMMENT '调用方式 1：同步 2：异步（需主动回查）',

	channelFlow CHAR(28) NOT NULL PRIMARY KEY COMMENT '同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）',

	channelDateTime DATETIME NOT NULL COMMENT '渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期',

	transCode CHAR(8) NOT NULL COMMENT '交易码',
	
	/*--- 以下为Request 报文体 部分  ---*/

	appId CHAR(3) NOT NULL DEFAULT 'PC' COMMENT '应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX',

	loanNo BIGINT UNSIGNED NOT NULL COMMENT '借款编号',

	bwAcName VARCHAR(128) NOT NULL COMMENT '借款人姓名',

	bwAcNo VARCHAR(32) NOT NULL COMMENT '借款人华兴银行e账户账号',

	feeAmt DECIMAL(15,2) NOT NULL DEFAULT 0.00 COMMENT '手续费',

	amount DECIMAL(15,2) NOT NULL COMMENT '还款金额',

	remark VARCHAR(60) COMMENT '备注',
	
	/*--- 以下为Response 报文头 部分  ---*/

	serverFlow CHAR(20) COMMENT '服务流水号',

	serverDateTime DATETIME COMMENT '服务日期 + 服务时间',

	status SMALLINT COMMENT '业务状态',

	errorCode VARCHAR(12) COMMENT '错误代码',

	errorMsg VARCHAR(300) COMMENT '错误信息,错误代码非0时，返回具体的错误原因',
	
	/*--- 以下为Response 报文体 部分  ---*/

	resJnlNo VARCHAR(32) COMMENT '银行交易流水号',

	transDateTime DATETIME COMMENT '交易日期 + 交易时间',

	extFiled1 VARCHAR(200) COMMENT '备用字段1',

	extFiled2 VARCHAR(200) COMMENT '备用字段2',

	extFiled3 VARCHAR(200) COMMENT '备用字段3'

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 6.36	还款收益明细提交(OGW00074)
 */
CREATE TABLE ghbank_ogw00074(

    /*--- 以下为Request 报文头 部分  ---*/

	priority SMALLINT NOT NULL DEFAULT 1 COMMENT '优先级 1：普通 2：紧急 3：特急',

	invokeMethod SMALLINT NOT NULL DEFAULT 1 COMMENT '调用方式 1：同步 2：异步（需主动回查）',

	channelFlow CHAR(28) NOT NULL PRIMARY KEY COMMENT '同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）',

	channelDateTime DATETIME NOT NULL COMMENT '渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期',

	transCode CHAR(8) NOT NULL COMMENT '交易码',
	
	/*--- 以下为Request 报文体 部分  ---*/

	appId CHAR(3) NOT NULL DEFAULT 'PC' COMMENT '应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX',

	oldReqSeqNo VARCHAR(28) NOT NULL COMMENT '原还款交易流水号',

	dfFlag SMALLINT NOT NULL COMMENT '还款类型 1=正常还款 2=垫付后，借款人还款',

	loanNo BIGINT UNSIGNED NOT NULL COMMENT '借款编号',

	bwAcName VARCHAR(128) NOT NULL COMMENT '借款人姓名',

	bwAcNo VARCHAR(32) NOT NULL COMMENT '借款人华兴银行e账户账号',

	totalNum INT NOT NULL COMMENT '总笔数',

	/*--- 以下为Response 报文头 部分  ---*/

	serverFlow CHAR(20) COMMENT '服务流水号',

	serverDateTime DATETIME COMMENT '服务日期 + 服务时间',

	status SMALLINT COMMENT '业务状态',

	errorCode VARCHAR(12) COMMENT '错误代码',

	errorMsg VARCHAR(300) COMMENT '错误信息,错误代码非0时，返回具体的错误原因',
	
	/*--- 以下为Response 报文体 部分  ---*/

	resJnlNo VARCHAR(32) COMMENT '银行交易流水号',

	transDateTime DATETIME COMMENT '交易日期 + 交易时间',

	extFiled1 VARCHAR(200) COMMENT '备用字段1',

	extFiled2 VARCHAR(200) COMMENT '备用字段2',

	extFiled3 VARCHAR(200) COMMENT '备用字段3',
	
	INDEX idx_ghbank_ogw00074_loanNo (loanNo)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 6.36	还款收益明细
 */
CREATE TABLE ghbank_ogw00074_repayItem(

	oldReqSeqNo CHAR(28) NOT NULL COMMENT '原还款收益明细提交交易流水号',

	subSeqNo CHAR(28) NOT NULL PRIMARY KEY COMMENT '子流水号',

	acNo VARCHAR(32) NOT NULL COMMENT '投资人华兴银行e账户账号',

	acName VARCHAR(64) NOT NULL COMMENT '投资人账号户名',

	incomeDate DATE COMMENT '该收益所属截止日期',

	amount DECIMAL(15,2) NOT NULL COMMENT '还款总金额',

	principalAmt DECIMAL(15,2) COMMENT '本次还款本金',

	incomeAmt DECIMAL(15,2) COMMENT '本次还款收益',

	feeAmt DECIMAL(10,2) COMMENT '本次还款费用',

	status CHAR(1) COMMENT '状态 L 待处理 R正在处理 N 未明 F失败 S成功',

	errorMsg VARCHAR(128) COMMENT '错误原因',

	transDateTime DATETIME COMMENT '银行交易日期+时间',

	hostJnlNo VARCHAR(32) COMMENT '银行交易流水号',

	extFiled3 VARCHAR(200) COMMENT '备用字段3',
	
	INDEX idx_ghbank_ogw00074_repayItem_oldReqSeqNo (oldReqSeqNo),
	
	INDEX idx_ghbank_ogw00074_repayItem_acNo (acNo)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 6.37	还款收益结果查询 (OGW00075)
 */
CREATE TABLE ghbank_ogw00075(

    /*--- 以下为Request 报文头 部分  ---*/

	priority SMALLINT NOT NULL DEFAULT 1 COMMENT '优先级 1：普通 2：紧急 3：特急',

	invokeMethod SMALLINT NOT NULL DEFAULT 1 COMMENT '调用方式 1：同步 2：异步（需主动回查）',

	channelFlow CHAR(28) NOT NULL PRIMARY KEY COMMENT '同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）',

	channelDateTime DATETIME NOT NULL COMMENT '渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期',

	transCode CHAR(8) NOT NULL COMMENT '交易码',
	
	/*--- 以下为Request 报文体 部分  ---*/

	appId CHAR(3) NOT NULL DEFAULT 'PC' COMMENT '应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX',

	oldReqSeqNo VARCHAR(28) NOT NULL COMMENT '原流水号',

	loanNo BIGINT UNSIGNED NOT NULL COMMENT '借款编号',

	subSeqNo VARCHAR(28) COMMENT '子流水号',
	
	/*--- 以下为Response 报文头 部分  ---*/

	serverFlow CHAR(20) COMMENT '服务流水号',

	serverDateTime DATETIME COMMENT '服务日期 + 服务时间',

	status SMALLINT COMMENT '业务状态',

	errorCode VARCHAR(12) COMMENT '错误代码',

	errorMsg VARCHAR(300) COMMENT '错误信息,错误代码非0时，返回具体的错误原因',

	/*--- 以下为Response 报文体 部分  ---*/
	
	returnStatus CHAR(1) COMMENT '交易状态',

	returnErrorMsg VARCHAR(32) COMMENT '失败原因',
	
	repayReqSeqNo VARCHAR(28) NULL COMMENT '还款交易流水号',

	dfFlag SMALLINT NULL COMMENT '还款类型 1=正常还款 2=垫付后，借款人还款',

	bwAcName VARCHAR(128) NULL COMMENT '还款账号户名',

	bwAcNo VARCHAR(32) NULL COMMENT '还款账号',

	totalNum INT UNSIGNED NULL COMMENT '总笔数',

	extFiled1 VARCHAR(200) COMMENT '备用字段1',

	extFiled2 VARCHAR(200) COMMENT '备用字段2',

	extFiled3 VARCHAR(200) COMMENT '备用字段3'

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/**
 * 6.38	单笔奖励或分红（可选）(OGW00076)
 */
CREATE TABLE ghbank_ogw00076(

	priority SMALLINT NOT NULL DEFAULT 1 COMMENT '优先级 1：普通 2：紧急 3：特急',

	invokeMethod SMALLINT NOT NULL DEFAULT 1 COMMENT '调用方式 1：同步 2：异步（需主动回查）',

	channelFlow CHAR(28) NOT NULL COMMENT '同一渠道不能重复上送，此为固定长度28位。(商户发送的流水【格式：渠道标识+YYYYMMDD+所发交易的“交易码”的最后三位+11位商户流水)保证流水的唯一性】）',

	channelDateTime DATETIME NOT NULL COMMENT '渠道日期 yyyyMMdd + 渠道时间 HHmmss。注意日期为实际交易的北京标准时间的日期',

	transCode CHAR(8) NOT NULL COMMENT '交易码',

	appId CHAR(3) NOT NULL DEFAULT 'PC' COMMENT '应用标识:个人电脑:PC（不送则默认PC）手机：APP 微信：WX',

	acNo VARCHAR(32) COMMENT '收款人华兴银行e账户账号',

	acName VARCHAR(128) COMMENT '收款户名',

	amount DECIMAL(15,2) NOT NULL COMMENT '交易金额',

	remark VARCHAR(60) COMMENT '备注',

	serverFlow CHAR(20) COMMENT '服务流水号',

	serverDateTime DATETIME COMMENT '服务日期 + 服务时间',

	status SMALLINT COMMENT '业务状态',

	errorCode VARCHAR(12) COMMENT '错误代码',

	errorMsg VARCHAR(300) COMMENT '错误信息,错误代码非0时，返回具体的错误原因',

	resJnlNo VARCHAR(32) COMMENT '银行交易流水号',

	transDateTime DATETIME COMMENT '交易日期 + 交易时间',

	extFiled1 VARCHAR(200) COMMENT '备用字段1',

	extFiled2 VARCHAR(200) COMMENT '备用字段2',

	extFiled3 VARCHAR(200) COMMENT '备用字段3'

) ENGINE=InnoDB DEFAULT CHARSET=utf8;




/*------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                             系统数据                                                                                                                                        */
/*------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/



/*
 * 银行代码，及代扣限额表
 * */
CREATE TABLE sys_bank (

    code SMALLINT UNSIGNED NOT NULL PRIMARY KEY COMMENT '银行代码。如102为工行，103为农行等',
    
    shortName VARCHAR(8) NOT NULL COMMENT '银行简称，如“工商银行”，“招商银行”',
    
    fullName VARCHAR(32) NOT NULL COMMENT '银行全称',
    
    payeaseDeductSingleLimit INT UNSIGNED NOT NULL COMMENT '首信易代扣-单笔限额',
    
    payeaseDeductDailyLimit INT UNSIGNED NOT NULL COMMENT '首信易代扣-每日限额',
    
    unspayDeductSingleLimit INT UNSIGNED NOT NULL COMMENT '银生宝代扣-单笔限额',
    
    unspayDeductDailyLimit INT UNSIGNED NOT NULL COMMENT '银生宝代扣-每日限额'
    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*
 * 系统管理员
 * */
CREATE TABLE sys_staff (

    id INT UNSIGNED NOT NULL PRIMARY KEY COMMENT '员工Id，员工最重要的标示，主键（不是自增）',

    password CHAR(32) NOT NULL COMMENT '登录密码，MD5 32位',

    staffName VARCHAR (16) NOT NULL COMMENT '员工姓名',

    idCardNo CHAR(18) NOT NULL COMMENT '身份证号',

    mobile BIGINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '联系电话',

    weChat VARCHAR(32) NULL COMMENT '微信，停用',

    email VARCHAR(32) NULL COMMENT '邮件，要求唯一',

    UNIQUE INDEX idx_staff_mobile(mobile),

    UNIQUE INDEX idx_staff_email(email)

) ENGINE=InnoDB DEFAULT CHARSET=utf8;

/*本表中的银行全称，以首信易认定的银行全称为准*/
INSERT INTO sys_bank VALUES
(102, '工商银行',    '中国工商银行股份有限公司',   50000,     50000,    50000,     50000),
(103, '农业银行',    '中国农业银行股份有限公司', 200000,   500000, 200000,   500000),
(104, '中国银行',    '中国银行股份有限公司',          50000,   500000,   50000,    500000),
(105, '建设银行',    '中国建设银行股份有限公司', 200000, 1000000, 100000, 1000000),
(201, '国开行',        '国家开发银行',                     100000, 1000000, 100000, 1000000),
(202, '进出口银行', '中国进出口银行',                  100000, 1000000, 100000, 1000000),
(203, '农发行',        '中国农业发展银行',              100000, 1000000, 100000, 1000000),
(301, '交通银行',     '交通银行',        300000, 1000000, 100000, 1000000),
(302, '中信银行',     '中信银行',                      500000, 1000000, 500000, 1000000),
(303, '光大银行',     '中国光大银行股份有限公司', 500000, 1000000, 100000, 1000000),
(304, '华夏银行',     '华夏银行股份有限公司',        100000, 1000000, 100000, 1000000),
(305, '民生银行',     '中国民生银行股份有限公司', 200000,   500000, 200000,   500000),
(306, '广发银行',     '广东发展银行股份有限公司', 500000, 1000000, 500000, 1000000),
(307, '平安银行',     '平安银行股份有限公司',        500000, 1000000, 500000, 1000000),
(308, '招商银行',     '招商银行股份有限公司',        100000, 5000000, 100000, 1000000),
(309, '兴业银行',     '兴业银行股份有限公司',        100000, 1000000, 100000, 1000000),
(310, '浦发银行',     '上海浦东发展银行',                   5000,        5000,      5000,       5000),
(313, '城商行',        '城市商业银行',                      100000, 1000000, 100000, 1000000),
(314, '农商行',        '农村商业银行',                      100000, 1000000, 100000, 1000000),
(401, '城信社',        '城市信用社',                          100000, 1000000, 100000, 1000000),
(402, '农信社',        '农村信用社',                          100000, 1000000, 100000, 1000000),
(403, '邮储银行',     '中国邮政储蓄银行有限责任公司',                 10000, 5000000,   10000, 5000000);


INSERT INTO sys_staff VALUES
('111002','5851CB319A3A19E3D7487FC6CD7DEFE5','于国栋','110101198501019902','18511819507','','yuguodong@yuanheng100.com'),
('111008','5851CB319A3A19E3D7487FC6CD7DEFE5','张鸣焕','110228199307113821','18210158248','','zhangminghuan@yuanheng100.com'),
('115001','5851CB319A3A19E3D7487FC6CD7DEFE5','柏松','370102198004222910','13521182597','','baisong@yuanheng100.com');


/*------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/
/*                                                                                                                              数据库账号，权限部分                                                                                                                                        */
/*------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------*/

/*创建settlement数据库用户权限*/
GRANT CREATE, SELECT, INSERT, UPDATE  ON settlement.* TO settlement@localhost IDENTIFIED BY 'yhst@203';
GRANT CREATE, SELECT, INSERT, UPDATE  ON settlement.* TO settlement@127.0.0.1 IDENTIFIED BY 'yhst@203';

/*创建备份数据库用户*/
GRANT SELECT, LOCK TABLES  ON settlement.* TO settlementbakup@localhost IDENTIFIED BY 'yhst@203';
GRANT SELECT, LOCK TABLES  ON settlement.* TO settlementbakup@127.0.0.1 IDENTIFIED BY 'yhst@203';
/* 刷新权限 */
FLUSH PRIVILEGES;