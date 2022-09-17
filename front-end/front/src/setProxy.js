const { createProxyMiddleware } = require('http-proxy-middleware');

module.exports = function(app) {
  app.use(
    '/api',
    createProxyMiddleware({
      target: 'http://localhost:8080',	# 서버 URL or localhost:설정한포트번호
      changeOrigin: true,
    })
  );
};

#api 로 시작하면 프록시 미들웨어 실행