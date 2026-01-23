const express = require("express");
const cors = require("cors");
const { createProxyMiddleware } = require("http-proxy-middleware");

const app = express();
const PORT = 7777;



console.log(createProxyMiddleware, 100)

// 开启跨域
app.use(cors());
app.use(express.static('html/sky'));
// 测试接口：延迟 2 秒返回数据

// —— 添加代理 ——
// 假设你静态文件里请求 "/api/xxx"，希望转发到本机 8080
// —— 管理端代理 /api/* → http://localhost:8080/admin/*
app.use(
    '/api',
    createProxyMiddleware({
        target: 'http://localhost:8080/admin',
        changeOrigin: true,
        pathRewrite: (path, req) => {
            return path;
        },
        logLevel: 'debug'
    })
);

// —— 用户端代理 /user/* → http://webservers/user/*
app.use(
    '/user',
    createProxyMiddleware({
        target: 'http://webservers', // 可改成真实 IP 或域名
        changeOrigin: true,
        pathRewrite: {
            '^/user': '/user'   // /user/xxx → /user/xxx （可省略）
        },
        logLevel: 'debug'
    })
);

// —— WebSocket 代理 /ws/* → http://webservers/ws/*
app.use(
    '/ws',
    createProxyMiddleware({
        target: 'http://webservers',
        changeOrigin: true,
        ws: true,                      // 开启 WebSocket 支持
        pathRewrite: {
            '^/ws': '/ws'
        },
        logLevel: 'debug'
    })
);


// 启动服务器
app.listen(PORT, () => {
  console.log(`Mock server running at http://localhost:${PORT}`);
});