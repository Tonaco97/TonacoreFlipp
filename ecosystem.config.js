module.exports = {
  apps : [{
    name: "tonacorp-relay",
    script: "./server/relay.js",
    instances: "max",
    exec_mode: "cluster",
    env: {
      NODE_ENV: "production",
      PORT: 8080
    },
    error_file: "./logs/err.log",
    out_file: "./logs/out.log",
    log_date_format: "YYYY-MM-DD HH:mm:ss Z" // ISO 8601 compliance
  }]
}
