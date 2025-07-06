# Glasp Backend

This is the backend service for the Glasp platform. It provides user management functionality for the Glasp frontend, including user authentication, account creation, and the ability for users to generate tokens to access the Glasp Search backend.

## Features

- **User Management**: Handles user registration, login, and profile management.
- **Token Generation**: Allows users to generate tokens for secure access to the [Glasp Search](https://github.com/lukas8920/glasp-search) backend.
- **API**: Exposes authentication and user management APIs for use by the Glasp frontend.

## Related Projects

- [Glasp Frontend Repository](https://github.com/glaspco/glasp-frontend)
- [Glasp Search Backend](https://github.com/lukas8920/glasp-search)

## Usage

The backend exposes endpoints for user registration, authentication, and token management. These endpoints are consumed by the Glasp frontend application.

To generate a token for the Glasp Search backend, users must be authenticated and then can use the relevant endpoint provided by this backend.

## Contributing

Contributions are welcome! Please submit a pull request or open an issue to discuss changes.

## License

This project is licensed under the MIT License.